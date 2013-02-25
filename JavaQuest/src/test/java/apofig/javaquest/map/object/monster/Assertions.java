package apofig.javaquest.map.object.monster;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/25/13
 * Time: 11:37 PM
 */
public class Assertions {
    public static void assertMonster(int[] expected, CodeRunnerMonster monster) {
        for (int index = 0; index < expected.length; index ++) {
            assertEquals("At index " + index,  String.valueOf(expected[index]), monster.method(index));
        }
    }
}
