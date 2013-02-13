package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.MethodRunner;
import apofig.javaquest.map.MonsterTest;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/8/13
 * Time: 2:07 PM
 */
public class FizzBuzzMonsterTest implements MonsterTest {
    private void addWarning(List<String> messages, int index, String expected, Object actual) {
        messages.add(String.format("For %s must be returns “%s” but was “%s”", index, expected, actual));
    }

    @Override
    public String test(MethodRunner runner) {
        List<String> messages = new LinkedList<String>();
        for (int i = 0; i <= 100; i++) {
            Object actual = runner.run(i);
            if (i % (3 * 5) == 0) {
                if (!actual.equals("FizzBuzz")) {
                    addWarning(messages, i, "FizzBuzz", actual);
                }
            } else if (i % 3 == 0) {
                if (!actual.equals("Fizz")) {
                    addWarning(messages, i, "Fizz", actual);
                }
            } else if (i % 5 == 0) {
                if (!actual.equals("Buzz")) {
                    addWarning(messages, i, "Buzz", actual);
                }
            } else {
                if (!actual.equals(String.valueOf(i))) {
                    addWarning(messages, i, String.valueOf(i), actual);
                }
            }
            if (messages.size() > 6) {
                messages.add("...");
                break;
            }
        }
        if (messages.size() == 0) {
            return "OK";
        }
        return messages.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", "\n");
    }
}
