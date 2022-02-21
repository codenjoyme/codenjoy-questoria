package com.codenjoy.dojo.questoria.model;

import java.util.LinkedList;
import java.util.List;

public class Messages {
    private static final String SEPARATOR = "---------------------------------------------------------------\n";
    private List<String> messages = new LinkedList<String>();
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
        StringBuffer result = new StringBuffer();
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
