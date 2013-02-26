package apofig.javaquest.map.object.monster;

import static apofig.javaquest.map.object.monster.FizzBuzzMonster.*;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.Gold;
import apofig.javaquest.map.object.ObjectFactory;
import apofig.javaquest.map.object.Place;
import apofig.javaquest.map.object.Something;
import apofig.javaquest.map.object.monster.CodeRunnerMonster;
import apofig.javaquest.map.object.monster.FizzBuzzMonster;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: oleksandr.baglai
 * Date: 2/8/13
 * Time: 1:21 PM
 */
public class OneIntCodeRunnerMonsterTest {

    public static final String BAD_CODE = "public String fizzbuzz(int i) {" +
            "return (i % 3 == 0)?\"Fizz\":String.valueOf(i);" +
            "}";

    public static final String BAD_CODE_WARNINGS = "Для [5] метод должен вернуть “Buzz”, но ты вернул “5”\n" +
            "Для [10] метод должен вернуть “Buzz”, но ты вернул “10”\n" +
            "Для [15] метод должен вернуть “FizzBuzz”, но ты вернул “Fizz”\n" +
            "Для [20] метод должен вернуть “Buzz”, но ты вернул “20”\n" +
            "Для [25] метод должен вернуть “Buzz”, но ты вернул “25”\n" +
            "Для [30] метод должен вернуть “FizzBuzz”, но ты вернул “Fizz”\n" +
            "Для [35] метод должен вернуть “Buzz”, но ты вернул “35”\n" +
            "...\n";
    private Messages messages;
    private CodeRunnerMonster monster;


    private boolean die = false;

    @Test
    public void shouldPrintErrorWhenNotPass() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(BAD_CODE,
                "FizzBuzzMonster: " + BAD_CODE_WARNINGS +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintExceptionWhenBadCode() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer("",
                "FizzBuzzMonster: Expected one method!\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintExceptionWhenBadCode2() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "String fizzbuzz(int i) {\n" +
                "    return String.valueOf(i);\n" +
                "}",
                "FizzBuzzMonster: java.lang.IllegalAccessException: Class apofig.compiler.JavaMethod can not access a member of class Dynamic with modifiers \"\"\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintExceptionWhenBadCode3() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "public String fizzbuzz(int i) {\n" +
                "     return (i  == 0)\\?\"Fizz\":String.valueOf(i);\n" +
                "}",
                "FizzBuzzMonster: /Dynamic.java:2: error: illegal character: \\92\r\n" +
                "     return (i  == 0)\\?\"Fizz\":String.valueOf(i);\r\n" +
                "                     ^\r\n" +
                "1 error\r\n" +
                "\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintOkAndDieWhenOk() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(OK_CODE,
                "FizzBuzzMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                        "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    private void assertMonsterDie() {
        assertTrue(die);
    }

    private void assertMonsterHelpMeWithMyAnswer(String myAnswer, String expectedHelp) {
        monster.answer(myAnswer);
        assertEquals(expectedHelp, messages.get());
        messages.clear();
    }

    private void assertMonsterAskMe(String expected) {
        monster.askMe();
        assertEquals(expected, messages.get());
        messages.clear();
    }

    private void buildMonster(String question, String help) {
        monster = new FizzBuzzMonster(
                new Action() {
                    @Override
                    public void act(Something object) {
                        die = true;
                    }
                });
        monster.setFactory(new ObjectFactory() {
            @Override
            public Something make(char c, Place place) {
                Gold gold = new Gold();
                gold.setMessages(messages);
                gold.setFactory(this);
                return gold;
            }
        });
        monster.setPlace(new Place() {
            @Override
            public void update(char newChar) {

            }
        });
        messages = new Messages();
        monster.setMessages(messages);
    }
}
