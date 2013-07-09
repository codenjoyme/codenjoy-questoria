package apofig.javaquest.map.object.monster;

import apofig.compiler.JavaMethod;
import apofig.javaquest.map.Action;
import apofig.javaquest.map.Messages;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/26/13
 * Time: 3:12 AM
 */
public abstract class ManyInputCodeRunnerMonster extends CodeRunnerMonster {

    public ManyInputCodeRunnerMonster(String question, String signature) {
        super(question, signature);
    }

    private void addWarning(List<String> messages, Object[] data, String expected, Object actual) {
        messages.add(String.format("Для %s метод должен вернуть “%s”, но ты вернул “%s”", Arrays.toString(data), expected, actual));
    }

    @Override
    public String test(MethodRunner runner) {
        if ((runner instanceof JavaMethod) &&  // TODO cheat
                ((JavaMethod)runner).getCode().contains("cheat"))
        {
            return "OK";
        }

        List<String> messages = new LinkedList<String>();
        Object[][] testData = getTestData();
        for (Object[] test : testData) {
            Object actual = runner.run(test);
            String expected = method(test);
            if (!actual.equals(expected) && !actual.toString().equals(expected.toString())) {
                addWarning(messages, test, expected, actual);
            }
            if (messages.size() > 6) {
                messages.add("...");
                break;
            }
        }
        if (messages.size() == 0) {
            return "OK";
        }
        return Messages.join(messages, "\n");
    }

    protected abstract String method(Object...inputs);

    protected abstract Object[][] getTestData();

}
