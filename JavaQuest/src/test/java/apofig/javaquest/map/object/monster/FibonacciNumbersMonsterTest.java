package apofig.javaquest.map.object.monster;

import org.junit.Test;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 11:42 PM
 */
public class FibonacciNumbersMonsterTest {

    @Test
    public void shouldWork() {
        int[] expected = new int[]{0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144,
                233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711};
        Assertions.assertMonster(expected, new FibonacciNumbersMonster(null));
    }
}
