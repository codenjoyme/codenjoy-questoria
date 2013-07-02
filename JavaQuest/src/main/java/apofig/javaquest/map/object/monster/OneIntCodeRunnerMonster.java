package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

/**
 * User: oleksandr.baglai
 * Date: 2/26/13
 * Time: 3:12 AM
 */
public abstract class OneIntCodeRunnerMonster extends ManyInputCodeRunnerMonster {

    public OneIntCodeRunnerMonster(String question, Action onKill) {
        super(question,  onKill);
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