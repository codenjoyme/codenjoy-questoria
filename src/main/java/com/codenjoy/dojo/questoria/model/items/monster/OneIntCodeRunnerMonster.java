package com.codenjoy.dojo.questoria.model.items.monster;

public abstract class OneIntCodeRunnerMonster extends ManyInputCodeRunnerMonster {

    public static int COUNT_TESTS = 100;

    public static String SIGNATURE =
            "public String method(int number) {\n" +
            "    return |;\n" +
            "}";

    public OneIntCodeRunnerMonster(String question, int weight) {
        super(question, SIGNATURE, weight);
    }

    protected Object[][] getTestData() {
        Object[][] result = new Object[COUNT_TESTS][1];
        for (int index = 0; index < result.length; index ++) {
            result[index][0] = index + 1;
        }
        return result;
    }

    protected String method(Object... input) {
        return method((Integer)input[0]);
    }

    protected abstract String method(int input);

}
