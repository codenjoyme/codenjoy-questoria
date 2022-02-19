package apofig.javaquest.field.object.monster;

import apofig.compiler.JavaCompiler;
import apofig.javaquest.field.object.monster.test.TestResult;
import apofig.javaquest.field.object.monster.test.TestSuite;

public abstract class CodeRunnerMonster extends Monster implements MonsterTest {

    public final static String HELP = "Попробуй еще раз!";
    public final static String LEAVE = "Просто так ты не уйдешь!";
    public static final String ANSWER = "4E0A";

    protected MonsterTest test;
    protected TestSuite testSuite = new TestSuite();

    public CodeRunnerMonster(String question, String signature, int weight) {
        super(question, ANSWER, HELP, LEAVE, signature, weight);
        this.test = this;
    }

    @Override
    public void answer(String code) {
        if (code.equals(ANSWER)) { // TODO подумать и упростить этот костыль с 4E0A/OK и другими константами
            super.answer(code);
            return;
        }
        try {
            MethodRunner method = compile(code);
            Object[][] testData = getTestData();
            for (Object[] data : testData) {
                TestResult testResult = test.test(data, method);
                testSuite.add(testResult);
                if (testSuite.isEnough()) break;
            }
        } catch (Exception e) {
            testSuite.add(new TestResult(e));
        }
        processResults(testSuite.getResults());
        testSuite.clear();
    }

    protected abstract Object[][] getTestData();

    protected void processResults(String message) {
        if (message.equals("OK")) {
            super.answer("4E0A");
        } else {
            messenger.say(message);
            messenger.say(help);
        }
    }

    private MethodRunner compile(String code) {
        JavaCompiler compiler = new JavaCompiler();
        return compiler.getMethod(code);
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }
}
