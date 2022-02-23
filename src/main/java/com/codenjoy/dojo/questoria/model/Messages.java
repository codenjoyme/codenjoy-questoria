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

import java.util.LinkedList;
import java.util.List;

public class Messages {

    private static final String SEPARATOR = "---------------------------------------------------------------\n";

    private List<String> messages = new LinkedList<>();
    private String owner;

    public Messages() {}

    public Messages(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return getLast(messages.size());
    }

    public static String withoutSeparator(String message) {
        return message.replaceAll(SEPARATOR, "");
    }

    public String getLast(int count) {
        if (messages.isEmpty()) {
            return "";
        }
        int size = messages.size();
        if (count > size) {
            count = size;
        }
        List<String> strings = messages.subList(size - count, size);
        return join(strings, "\n" + SEPARATOR);
    }

    public static String join(List<String> strings, String separator) {
        StringBuilder result = new StringBuilder();
        int count = strings.size();
        for (String string : strings) {
            result.append(string);
            count--;
            if (count > 0) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    public void clear() {
        messages.clear();
    }

    public String get() {
        return toString();
    }

    public void addOnce(String message) {
        if (!isLast(message)) {
            messages.add(message);
        }
    }

    public void add(String message) {
        messages.add(message);
    }

    private boolean isLast(String message) {
        if (messages.isEmpty()) {
            return false;
        }
        return messages.lastIndexOf(message) == messages.size() - 1;
    }
}
