package apofig.javaquest.field.object.monster;

import apofig.compiler.JavaMethod;
import apofig.javaquest.field.*;
import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.MessengerImpl;
import apofig.javaquest.field.object.ObjectFactory;
import apofig.javaquest.field.object.World;
import apofig.javaquest.field.object.impl.Nothing;
import apofig.javaquest.field.object.monster.test.TestResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CodeRunnerMonsterTest {

    private CodeRunnerMonster monster;
    private MessengerImpl messenger;
    private String answer;
    private String question;

    @Before
    public void setup() {
        question = "question?";
        answer = "answer!";
        String signature = "blablabla";
        int weight = 1;

        monster = new CodeRunnerMonster(question, signature, weight) {
            @Override
            protected Object[][] getTestData() {
                return new Object[0][0];
            }

            @Override
            public TestResult test(Object[] test, MethodRunner runner) {
                if ((runner instanceof JavaMethod) &&
                        ((JavaMethod)runner).getCode().contains(answer))
                {
                    return new TestResult(test, "data", "data");
                }
                return null;
            }

            @Override
            public String getName() {
                return "Monster";
            }
        };
        messenger = new MessengerImpl();
        Messages messages = new Messages();
        messenger.add(messages);
        monster.setMessenger(messenger);
        World world = mock(World.class);
        when(world.make(anyChar(), anyObject())).thenReturn(new Nothing());
        monster.setWorld(world);
    }

    @Test
    public void shouldKillMonster() {
        // when
        monster.answer(
                "public String method(int n) {\n" +
                "    return \"" + answer + "\";\n" +
                "}");

        // then
        assertMessage("Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!");
    }

    @Test
    public void shouldLeave_whenTryToLeaveMonster() {
        // given
        Me hero = mock(Me.class);
        monster.meetWith(hero);

        // when
        monster.tryToLeave(hero);

        // then
        assertMessage("Monster: Просто так ты не уйдешь!");

        // when
        monster.tryToLeave(hero);

        // then
        assertMessage("Monster: Монстр отнял у тебя немного золота...");
    }

    @Test
    public void shouldNoPortal_whenNewPlayer() {
        // given
        // when
        Me hero = createHero("me");

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero.getInfo().toString());
    }

    @Test
    public void shouldOpenPortal_whenMeetWithMonster() {
        // given
        Me hero = createHero("me");

        // when
        monster.meetWith(hero);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 37A1CBD6",
                hero.getInfo().toString());
    }

    @Test
    public void shouldPortalStillOpened_whenTryToLeaveMonster() {
        // given
        Me hero = createHero("me");
        monster.meetWith(hero);

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 37A1CBD6",
                hero.getInfo().toString());

        // when
        monster.tryToLeave(hero);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 37A1CBD6",
                hero.getInfo().toString());
    }

    @Test
    public void shouldClosePortal_whenLeaveMonster() {
        // given
        Me hero = createHero("me");
        monster.meetWith(hero);

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 37A1CBD6",
                hero.getInfo().toString());

        // when
        monster.leave(hero);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero.getInfo().toString());
    }

    @Test
    public void shouldClosePortal_whenKillMonster() {
        // given
        Me hero = createHero("me");
        monster.meetWith(hero);

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 37A1CBD6",
                hero.getInfo().toString());

        // when
        monster.die();

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero.getInfo().toString());
    }

    @Test
    public void shouldPortalHex_hasConstantLength() {
        // given
        Me hero = createHero("4hdsdf");
        monster.meetWith(hero);


        // when
        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 02BA5F03",
                hero.getInfo().toString());
    }

    @Test
    public void shouldEverythingIsOK_whenKillMonsterWithoutHero() {
        // given
        // when
        monster.die();

        // then
        // no NPE
    }

    @Test
    public void shouldDifferentPortals_whenOneMonsterAndTwoHeroes() {
        // given
        Me hero1 = createHero("hero1");
        Me hero2 = createHero("hero2");

        // when
        monster.meetWith(hero1);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 5D79560B",
                hero1.getInfo().toString());
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero2.getInfo().toString());

        // when
        monster.leave(hero1);
        monster.meetWith(hero2);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero1.getInfo().toString());
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: B754C04C",
                hero2.getInfo().toString());

    }

    @Test
    public void shouldMonsterBusyForSecondHero_whenOneHeroStillWithThem() {
        // given
        Me hero1 = createHero("hero1");
        Me hero2 = createHero("hero2");

        // when
        monster.meetWith(hero1);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 5D79560B",
                hero1.getInfo().toString());
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero2.getInfo().toString());

        // when
        // monster.leave(hero1); // still fight
        monster.meetWith(hero2);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 5D79560B",
                hero1.getInfo().toString());
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero2.getInfo().toString());
    }

    private Me createHero(String name) {
        Player info = new Player(name);
        return new Me(mock(ObjectFactory.class),
                mock(HeroField.class),
                mock(FieldLocator.class),
                mock(PlayerView.class),
                mock(Messages.class),
                0, 0,
                info);
    }

    private void assertMessage(String expected) {
        assertEquals(expected, messenger.getMessages().toString());
        messenger.getMessages().clear();
    }
}