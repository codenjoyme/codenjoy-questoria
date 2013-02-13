package apofig.javaquest.map;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: oleksandr.baglai
 * Date: 2/8/13
 * Time: 1:21 PM
 */
public class CodeRunnerMonsterTest {

    private static final String OK_CODE = "public String fizzbuzz(int i) {" +
                "String result = \"\";" +
                "if (i % 3 == 0) {" +
                    "result += \"Fizz\";" +
                "}" +
                "if (i % 5 == 0) {" +
                    "result += \"Buzz\";" +
                "}" +
                "if (result.length() == 0) {" +
                    "result = String.valueOf(i);" +
                "}" +
                "return result;" +
            "}";
    public static final String BAD_CODE = "public String fizzbuzz(int i) {" +
            "return (i % 3 == 0)?\"Fizz\":String.valueOf(i);" +
            "}";
    public static final String BAD_CODE_WARNINGS = "For 0 must be returns “FizzBuzz” but was “Fizz”\n" +
            "For 5 must be returns “Buzz” but was “5”\n" +
            "For 10 must be returns “Buzz” but was “10”\n" +
            "For 15 must be returns “FizzBuzz” but was “Fizz”\n" +
            "For 20 must be returns “Buzz” but was “20”\n" +
            "For 25 must be returns “Buzz” but was “25”\n" +
            "For 30 must be returns “FizzBuzz” but was “Fizz”\n" +
            "...\n";
    private Messages messages;
    private CodeRunnerMonster monster;

    final static String QUESTION = "Write a method “fizbuzz” that has one argument and \n" +
            "returns the string. For multiples of three retunr “Fizz” \n" +
            "instead of the number and for the multiples of five return \n" +
            "“Buzz”. For numbers which are multiples of both three \n" +
            "and five return “FizzBuzz”.";
    final static String HELP = "Please play with code again!";
    private boolean die = false;

    @Test
    public void shouldPrintErrorWhenNotPass() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("CodeRunnerMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(BAD_CODE, BAD_CODE_WARNINGS + HELP);
    }

    @Test
    public void shouldPrintOkAndDieWhenOk() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("CodeRunnerMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(OK_CODE,
                "CodeRunnerMonster: yOU @#& Ki$%@&^ll me $!@!\n" +
                "Gold: I am an 10$\n" +
                "Please play with code again!");
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
        monster = new CodeRunnerMonster(
                question,
                new FizzBuzzMonsterTest(), help,
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
