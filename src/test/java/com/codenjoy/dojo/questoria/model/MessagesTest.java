package com.codenjoy.dojo.questoria.model;

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

import org.junit.Test;

import static com.codenjoy.dojo.questoria.model.Messages.withoutSeparator;
import static junit.framework.Assert.assertEquals;

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
