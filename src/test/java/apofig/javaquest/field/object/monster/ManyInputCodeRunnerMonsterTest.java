package apofig.javaquest.field.object.monster;

import apofig.javaquest.field.Messages;
import apofig.javaquest.field.object.*;
import apofig.javaquest.field.object.impl.Gold;
import apofig.javaquest.field.object.monster.impl.LongDivisionMonster;
import org.junit.Test;

import static apofig.javaquest.field.Messages.withoutSeparator;
import static apofig.javaquest.field.object.monster.impl.LongDivisionMonster.HELP;
import static apofig.javaquest.field.object.monster.impl.LongDivisionMonster.QUESTION;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ManyInputCodeRunnerMonsterTest {

    public static final String BAD_CODE = "public String method(int a, int b) {" +
                "return \"0\";" +
            "}";

    public static final String BAD_CODE_WARNINGS =
            "Для [1, 2] метод должен вернуть “0.5”, но ты вернул “0”\n" +
            "Для [1, 1] метод должен вернуть “1”, но ты вернул “0”\n" +
            "Для [5, 5] метод должен вернуть “1”, но ты вернул “0”\n" +
            "Для [55, 5] метод должен вернуть “11”, но ты вернул “0”\n" +
            "Для [55, 44] метод должен вернуть “1.25”, но ты вернул “0”\n" +
            "Для [0, 56] метод работает правильно - ты вернул “0”\n" +
            "Для [56, 1] метод должен вернуть “56”, но ты вернул “0”\n";

    private Messages messages;
    private CodeRunnerMonster monster;

    private boolean die = false;

    @Test
    public void shouldPrintErrorWhenNotPass() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(BAD_CODE,
                "LongDivisionMonster: " + BAD_CODE_WARNINGS +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintExceptionWhenBadCode() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer("",
                "LongDivisionMonster: Exception: java.lang.IllegalArgumentException: Expected one method!\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintExceptionWhenBadCode2() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "String method(int a, int b) {\n" +
                "    return \"0\";\n" +
                "}",
                "LongDivisionMonster: Для [1, 2] метод сгенерировал Exception: java.lang.RuntimeException: java.lang.IllegalAccessException: Class apofig.compiler.JavaMethod can not access a member of class Dynamic with modifiers \"\"\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintExceptionWhenBadCode3() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "public String method(int a, int b) {\n" +
                "     return qwe;\n" +
                "}",
                "LongDivisionMonster: Exception: java.lang.RuntimeException: /Dynamic.java:2: error: cannot find symbol\r\n" +
                "     return qwe;\r\n" +
                "            ^\r\n" +
                "  symbol:   variable qwe\r\n" +
                "  location: class Dynamic\r\n" +
                "1 error\r\n" +
                "\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintOkAndDieWhenOk() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer("OK_CODE",
                "LongDivisionMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                        "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    private void assertMonsterDie() {
        assertTrue(die);
    }

    private void assertMonsterHelpMeWithMyAnswer(String myAnswer, String expectedHelp) {
        monster.answer(myAnswer);
        assertEquals(expectedHelp, withoutSeparator(messages.get()));
        messages.clear();
    }

    private void assertMonsterAskMe(String expected) {
        monster.ask();
        assertEquals(expected, messages.get());
        messages.clear();
    }

    private void buildMonster(String question, String help) {
        monster = new LongDivisionMonster();
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
