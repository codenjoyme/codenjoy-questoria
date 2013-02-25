package apofig.javaquest.map.object.monster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/25/13
 * Time: 11:42 PM
 */
public class PowerDigitSumMonsterTest {

    @Test
    public void shouldWork() {
        int[] primes = new int[] {1, 2, 4, 8, 7, 5, 10, 11, 13, 8, 7, 14, 19, 20, 22, 26, 25, 14, 19, 29, 31, 26, 25};
        Assertions.assertMonster(primes, new PowerDigitSumMonster(null));
    }


}
