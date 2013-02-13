package apofig.javaquest.map.object.monster;

import apofig.compiler.JavaCompiler;
import apofig.compiler.JavaMethod;
import apofig.javaquest.map.Action;
import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.monster.Monster;
import apofig.javaquest.map.object.monster.MonsterTest;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/6/13
 * Time: 7:13 PM
 */
public abstract class CodeRunnerMonster extends Monster implements MonsterTest {

    private MonsterTest test;
    private String help;

    public CodeRunnerMonster(String question, String help, Action onKill) {
        super(question, "4E0A", help, onKill);
        this.test = this;
        this.help = help;
    }

    @Override
    public void answer(String code) {
        String message = runTest(code);
        if (message.equals("OK")) {
            super.answer("4E0A");
        } else {
            say(message);
            say(help);
        }
    }

    private String runTest(String code) {
        JavaCompiler compiler = new JavaCompiler();
        JavaMethod method;
        try {
            method = compiler.getMethod(code);
            return test.test(method);
        } catch (Exception e) {
            if (e.getClass().equals(NullPointerException.class)) {
                return "Извини, NPE!";
            }
            return e.getMessage().replaceAll("Dynamic[0-9]+", "Dynamic"); // TODO hotfix
        }
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
