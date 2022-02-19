package apofig.javaquest.field.object.monster;

import apofig.javaquest.field.object.monster.test.TestResult;

public abstract class ManyInputCodeRunnerMonster extends WSCodeRunnerMonster {

    public ManyInputCodeRunnerMonster(String question, String signature, int weight) {
        super(question, signature, weight);
    }

    @Override
    public TestResult test(Object[] test, MethodRunner method) {
        try {
            Object actual = method.run(test);
            String expected = method(test);
            return new TestResult(test, actual, expected);
        } catch (Exception e) {
            return new TestResult(test, e);
        }
    }

    protected abstract String method(Object...inputs);

}
