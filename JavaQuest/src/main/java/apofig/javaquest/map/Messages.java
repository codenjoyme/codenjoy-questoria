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

    public String toString() {
        if (messages.isEmpty()) {
            return "";
        }
        return messages.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "\n");
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
