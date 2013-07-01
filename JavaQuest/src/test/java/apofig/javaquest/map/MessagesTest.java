package apofig.javaquest.map;

import org.junit.Test;

import static apofig.javaquest.map.Messages.withoutSeparator;
import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 8:56 PM
 */
public class MessagesTest {

    @Test
    public void shouldGetOnlyLastRecords() {
        Messages messages = new Messages();
        messages.add("one");
        messages.add("two");
        messages.add("three");
        messages.add("four");
        messages.add("five");
        messages.add("siz");
        messages.add("seven");

        assertMessage("one\n" +
                "two\n" +
                "three\n" +
                "four\n" +
                "five\n" +
                "siz\n" +
                "seven", messages.toString());

        assertMessage("four\n" +
                "five\n" +
                "siz\n" +
                "seven", messages.getLast(4));
    }

    private void assertMessage(String expected, String messages) {
        assertEquals(expected, withoutSeparator(messages));
    }

    @Test
    public void shouldLeaveCommasAndOtherSymbols() {
        Messages messages = new Messages();
        messages.add("o, n, e");
        messages.add("t[ ]o");
        messages.add("[three]");

        assertMessage("o, n, e\n" +
                "t[ ]o\n" +
                "[three]", messages.toString());
    }
}
