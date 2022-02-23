package com.codenjoy.dojo.questoria.model.items.monster;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.questoria.model.*;
import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.questoria.model.items.MessengerImpl;
import com.codenjoy.dojo.questoria.model.items.ObjectFactory;
import com.codenjoy.dojo.questoria.model.items.World;
import com.codenjoy.dojo.questoria.model.items.impl.Nothing;
import com.codenjoy.dojo.questoria.model.items.monster.test.TestResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyChar;
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
                return new Object[][]{
                        new Object[]{question},
                };
            }

            @Override
            public TestResult test(Object[] test, MethodRunner runner) {
                return new TestResult(test, answer, runner.run());
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
        when(world.make(anyChar(), any())).thenReturn(new Nothing());
        monster.setWorld(world);
    }

    @Test
    public void shouldKillMonster() {
        // when
        monster.answer(answer);

        // then
        assertMessage("Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!");
    }

    @Test
    public void shouldBadAnswer() {
        // when
        monster.answer("bad");

        // then
        assertMessage(
                "Monster: Для [question?] метод должен вернуть “bad”, но ты вернул “answer!”\n" +
                "---------------------------------------------------------------\n" +
                "Monster: Попробуй еще раз!");
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
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: A01D5385",
                hero.getInfo().toString());
    }

    @Test
    public void shouldPortalStillOpened_whenTryToLeaveMonster() {
        // given
        Me hero = createHero("me");
        monster.meetWith(hero);

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: A01D5385",
                hero.getInfo().toString());

        // when
        monster.tryToLeave(hero);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: A01D5385",
                hero.getInfo().toString());
    }

    @Test
    public void shouldClosePortal_whenLeaveMonster() {
        // given
        Me hero = createHero("me");
        monster.meetWith(hero);

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: A01D5385",
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

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: A01D5385",
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
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 2DBC7732",
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
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 1CCBFBFC",
                hero1.getInfo().toString());

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero2.getInfo().toString());

        // when
        monster.leave(hero1);
        monster.meetWith(hero2);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero1.getInfo().toString());

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 76A7663D",
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
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 1CCBFBFC",
                hero1.getInfo().toString());

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero2.getInfo().toString());

        // when
        // monster.leave(hero1); // still fight
        monster.meetWith(hero2);

        // then
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 1CCBFBFC",
                hero1.getInfo().toString());

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0",
                hero2.getInfo().toString());
    }

    private Me createHero(String name) {
        PlayerInfoImpl info = new PlayerInfoImpl(name);
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