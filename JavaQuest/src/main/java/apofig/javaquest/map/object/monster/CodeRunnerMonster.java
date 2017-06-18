package apofig.javaquest.map.object.monster;

import apofig.compiler.JavaCompiler;
import apofig.compiler.JavaMethod;

/**
 * User: oleksandr.baglai
 * Date: 2/6/13
 * Time: 7:13 PM
 */
public abstract class CodeRunnerMonster extends Monster implements MonsterTest {

    public final static String HELP = "Попробуй еще раз!";
    public final static String LEAVE = "Просто так ты не уйдешь!";

    private MonsterTest test;

    public CodeRunnerMonster(String question, String signature, int weight) {
        super(question, "4E0A", HELP, LEAVE, signature, weight);
        this.test = this;
    }

    @Override
    public void answer(String code) {
        String message = runTest(code);
        if (message.equals("OK")) {
            super.answer("4E0A");
        } else {
            messenger.say(message);
            messenger.say(help);
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
