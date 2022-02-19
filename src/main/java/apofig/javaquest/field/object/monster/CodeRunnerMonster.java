package apofig.javaquest.field.object.monster;

import apofig.javaquest.field.object.monster.test.TestResult;
import apofig.javaquest.field.object.monster.test.TestSuite;

import java.util.Arrays;
import java.util.List;

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
            List<String> answers = Arrays.asList(code.split("\n"));
            Object[][] testData = getTestData();
            for (int i = 0; i < testData.length; i++) {
                Object[] data = testData[i];
                String answer = answers.get(i);
                TestResult testResult = test.test(data, objects -> answer);
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

    public TestSuite getTestSuite() {
        return testSuite;
    }
}
