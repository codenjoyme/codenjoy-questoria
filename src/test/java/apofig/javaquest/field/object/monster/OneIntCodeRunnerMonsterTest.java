package apofig.javaquest.field.object.monster;

import apofig.javaquest.field.Messages;
import apofig.javaquest.field.object.*;
import apofig.javaquest.field.object.impl.Gold;
import apofig.javaquest.field.object.monster.impl.FizzBuzzMonster;
import org.junit.Test;

import static apofig.javaquest.field.Messages.withoutSeparator;
import static apofig.javaquest.field.object.monster.impl.FizzBuzzMonster.HELP;
import static apofig.javaquest.field.object.monster.impl.FizzBuzzMonster.QUESTION;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class OneIntCodeRunnerMonsterTest {

    public static final String BAD_CODE = "public String fizzbuzz(int i) {" +
            "return (i % 3 == 0)?\"Fizz\":String.valueOf(i);" +
            "}";

    public static final String BAD_CODE_WARNINGS = "Для [1] метод работает правильно - ты вернул “1”\n" +
            "Для [2] метод работает правильно - ты вернул “2”\n" +
            "Для [3] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [4] метод работает правильно - ты вернул “4”\n" +
            "Для [5] метод должен вернуть “Buzz”, но ты вернул “5”\n" +
            "Для [6] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [7] метод работает правильно - ты вернул “7”\n" +
            "Для [8] метод работает правильно - ты вернул “8”\n" +
            "Для [9] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [10] метод должен вернуть “Buzz”, но ты вернул “10”\n" +
            "Для [11] метод работает правильно - ты вернул “11”\n" +
            "Для [12] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [13] метод работает правильно - ты вернул “13”\n" +
            "Для [14] метод работает правильно - ты вернул “14”\n" +
            "Для [15] метод должен вернуть “FizzBuzz”, но ты вернул “Fizz”\n" +
            "Для [16] метод работает правильно - ты вернул “16”\n" +
            "Для [17] метод работает правильно - ты вернул “17”\n" +
            "Для [18] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [19] метод работает правильно - ты вернул “19”\n" +
            "Для [20] метод должен вернуть “Buzz”, но ты вернул “20”\n" +
            "Для [21] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [22] метод работает правильно - ты вернул “22”\n" +
            "Для [23] метод работает правильно - ты вернул “23”\n" +
            "Для [24] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [25] метод должен вернуть “Buzz”, но ты вернул “25”\n" +
            "Для [26] метод работает правильно - ты вернул “26”\n" +
            "Для [27] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [28] метод работает правильно - ты вернул “28”\n" +
            "Для [29] метод работает правильно - ты вернул “29”\n" +
            "Для [30] метод должен вернуть “FizzBuzz”, но ты вернул “Fizz”\n";

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
                "FizzBuzzMonster: Exception: java.lang.IllegalArgumentException: Expected one method!\n" +
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
                "FizzBuzzMonster: Для [1] метод сгенерировал Exception: java.lang.RuntimeException: java.lang.IllegalAccessException: Class apofig.compiler.JavaMethod can not access a member of class Dynamic with modifiers \"\"\n" +
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
                "FizzBuzzMonster: Exception: java.lang.RuntimeException: /Dynamic.java:2: error: illegal character: '\\'\r\n" +
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
        assertMonsterHelpMeWithMyAnswer("OK_CODE",
                "FizzBuzzMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                        "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    private void assertMonsterDie() {
        assertTrue(die);
    }

    private void assertMonsterHelpMeWithMyAnswer(String myAnswer, String expectedHelp) {
        monster.answer(myAnswer);
        assertMessage(expectedHelp, messages);
        messages.clear();
    }

    private void assertMessage(String expected, Messages messages) {
        assertEquals(expected, withoutSeparator(messages.get()));
    }

    private void assertMonsterAskMe(String expected) {
        monster.ask();
        assertMessage(expected, messages);
        messages.clear();
    }

    private void buildMonster(String question, String help) {
        monster = new FizzBuzzMonster();
        final Messenger messenger = new MessengerImpl();
        monster.setWorld(new World() {

            @Override
            public Place place() {
                return mock(Place.class);
            }

            @Override
            public void move(int x, int y) {
            }

            @Override
            public Something make(char c, Object... params) {
                die = true;
                Gold gold = new Gold();
                gold.setMessenger(messenger);
                gold.setWorld(this);
                gold.setParameters((Integer)params[0]);
                return gold;
            }
        });
        messages = new Messages();
        monster.setMessenger(messenger);
        monster.getMessenger().add(messages);
    }
}
