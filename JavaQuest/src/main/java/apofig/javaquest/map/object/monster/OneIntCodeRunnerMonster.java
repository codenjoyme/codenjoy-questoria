package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/26/13
 * Time: 3:12 AM
 */
public abstract class OneIntCodeRunnerMonster extends CodeRunnerMonster {

    public OneIntCodeRunnerMonster(String question, String help, Action onKill) {
        super(question, help, onKill);
    }

    private void addWarning(List<String> messages, int index, String expected, Object actual) {
        messages.add(String.format("Для %s метод должен вернуть “%s”, но ты вернул “%s”", index, expected, actual));
    }

    @Override
    public String test(MethodRunner runner) {
        List<String> messages = new LinkedList<String>();
        for (int i = 1; i <= 100; i++) {
            Object actual = runner.run(i);
            String expected = method(i);
            if (!actual.equals(expected)) {
                addWarning(messages, i, expected, actual);
            }
            if (messages.size() > 6) {
                messages.add("...");
                break;
            }
        }
        if (messages.size() == 0) {
            return "OK";
        }
        return Messages.toString(messages);
    }

    protected abstract String method(int input);

}
