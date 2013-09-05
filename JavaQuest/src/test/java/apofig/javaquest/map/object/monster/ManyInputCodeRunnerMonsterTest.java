package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.*;
import org.junit.Test;

import static apofig.javaquest.map.Messages.withoutSeparator;
import static apofig.javaquest.map.object.monster.LongDivisionMonster.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * User: oleksandr.baglai
 * Date: 2/26/13
 * Time: 1:21 PM
 */
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
            "Для [56, 1] метод должен вернуть “56”, но ты вернул “0”\n" +
            "Для [1, -2] метод должен вернуть “-0.5”, но ты вернул “0”\n" +
            "...\n";

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
                "LongDivisionMonster: Expected one method!\n" +
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
                "LongDivisionMonster: java.lang.IllegalAccessException: Class apofig.compiler.JavaMethod can not access a member of class Dynamic with modifiers \"\"\n" +
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
                "LongDivisionMonster: /Dynamic.java:2: error: cannot find symbol\r\n" +
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
        assertMonsterHelpMeWithMyAnswer(OK_CODE,
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
            public Something make(char c) {
                die = true;
                Gold gold = new Gold();
                gold.init(messenger);
                gold.add(messages);
                gold.setWorld(this);
                return gold;
            }
        });
        messages = new Messages();
        monster.init(messenger);
        monster.add(messages);
    }
}
