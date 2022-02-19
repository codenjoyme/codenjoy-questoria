package apofig.javaquest.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    @Test(expected = UnsupportedOperationException.class)
    public void shouldExceptionWhenNewObject() throws Exception {
        Colorizer.process("???Q????");
    }

    @Test
    public void shouldWrapWall() throws Exception {
        assertEquals("<span class=\"fog\">????<span class=\"wall\"># # # # </span>??</span>",
                Colorizer.process("????# # # # ??"));
    }


}
