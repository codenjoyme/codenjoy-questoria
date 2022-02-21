package com.codenjoy.dojo.questoria.model.items.monster;

import com.codenjoy.dojo.questoria.model.items.monster.test.TestResult;

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
