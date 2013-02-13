package apofig.javaquest.map;

import apofig.compiler.JavaCompiler;
import apofig.compiler.JavaMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * User: oleksandr.baglai
 * Date: 2/6/13
 * Time: 7:13 PM
 */
public class CodeRunnerMonster extends Monster {

    private MonsterTest test;
    private String help;

    public CodeRunnerMonster(String question, MonsterTest test, String help, Action onKill) {
        super(question, "4E0A", help, onKill);
        this.test = test;
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
                return "Sorry, NPE!";
            }
            return e.getMessage().replaceAll("Dynamic[0-9]+", "Dynamic"); // TODO hotfix
        }
    }


}
