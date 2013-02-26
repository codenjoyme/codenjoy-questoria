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
            System.out.println(e.toString());
            return e.getMessage().replaceAll("Dynamic[0-9]+", "Dynamic"); // TODO hotfix
        }
    }

}
