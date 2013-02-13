package apofig.javaquest.map;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:33 AM
 */
public class Messages {
    private List<String> messages = new LinkedList<>();

    @Override
    public String toString() {
        return getLast(messages.size());
    }

    public String getLast(int count) {
        if (messages.isEmpty()) {
            return "";
        }
        int size = messages.size();
        if (count > size) {
            count = size;
        }
        return messages.subList(size - count, size).toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "\n");
    }

    public void clear() {
        messages.clear();
    }

    public String get() {
        return toString();
    }

    public void add(String message) {
        if (!isLast(message)) {
            messages.add(message);
        }
    }

    private boolean isLast(String message) {
        if (messages.isEmpty()) {
            return false;
        }
        return messages.lastIndexOf(message) == messages.size() - 1;
    }
}
