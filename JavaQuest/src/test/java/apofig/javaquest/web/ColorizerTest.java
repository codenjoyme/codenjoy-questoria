package apofig.javaquest.web;

import org.approvaltests.Approvals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 10:16 PM
 */
public class ColorizerTest {

    @Test
    public void shouldWrapToSpan() throws Exception {
        assertEquals("<span class=\"fog\">???????</span>",
                Colorizer.process("???????"));
    }

    @Test
    public void shouldWrapToOtherSpanIfBetween() throws Exception {
        assertEquals("<span class=\"fog\">???<span class=\"gold\">$$$$$</span>????</span>",
                Colorizer.process("???$$$$$????"));
    }

    @Test
    public void shouldWrapToOtherSpanIfBetweenTwice() throws Exception {
        assertEquals("<span class=\"fog\">" +
                "???" +
                "<span class=\"gold\">" +
                    "$$$$$" +
                "</span>" +
                "??" +
                "<span class=\"monster\">" +
                    "@@@" +
                "</span>" +
                "??" +
                "</span>",
                Colorizer.process("???$$$$$??@@@??"));
    }

    @Test
     public void shouldIgnoreSpaces() throws Exception {
        assertEquals("<span class=\"fog\">" +
                "???" +
                "&nbsp;&nbsp;" +
                "<span class=\"iam\">" +
                "I" +
                "</span>" +
                "&nbsp;&nbsp;&nbsp;" +
                "????" +
                "</span>",
                Colorizer.process("???  I   ????"));
    }

    @Test
    public void shouldIgnoreNextLines() throws Exception {
        assertEquals("<span class=\"fog\">" +
                "???" +
                "<br>" +
                "????" +
                "</span>",
                Colorizer.process("???\n????"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldExceptionWhenNewObject() throws Exception {
        Colorizer.process("???Q????");
    }


}
