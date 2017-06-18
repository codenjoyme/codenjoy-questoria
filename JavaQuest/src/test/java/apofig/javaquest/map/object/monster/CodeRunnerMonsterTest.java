package apofig.javaquest.map.object.monster;

import apofig.compiler.JavaMethod;
import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Messenger;
import apofig.javaquest.map.object.MessengerImpl;
import apofig.javaquest.map.object.World;
import apofig.javaquest.map.object.impl.Gold;
import apofig.javaquest.map.object.impl.Nothing;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by indigo on 2017-06-18.
 */
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
            public String test(MethodRunner runner) {
                if ((runner instanceof JavaMethod) &&
                        ((JavaMethod)runner).getCode().contains(answer))
                {
                    return "OK";
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
    public void shouldTryToLeaveMonster() {
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

    private void assertMessage(String expected) {
        assertEquals(expected, messenger.getMessages().toString());
        messenger.getMessages().clear();
    }
}