package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

/**
 * User: oleksandr.baglai
 * Date: 2/26/13
 * Time: 3:12 AM
 */
public abstract class OneIntCodeRunnerMonster extends ManyInputCodeRunnerMonster {

    public static String SIGNATURE =
            "public String method(int number) {\n" +
            "    return |;\n" +
            "}";

    public OneIntCodeRunnerMonster(String question) {
        super(question, SIGNATURE);
    }

    protected Object[][] getTestData() {
        Object[][] result = new Object[100][1];
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
