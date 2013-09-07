package apofig.javaquest.map.object.monster;

import com.google.common.collect.Lists;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: sanja
 * Date: 07.09.13
 * Time: 14:59
 */
public class MonsterLoaderTest {

    @Test
    public void test() {
        assertEquals("[[Monster: FizzBuzzMonster with weight: 1], " +
                "[Monster: PrimeFactoryMonster with weight: 2], " +
                "[Monster: FibonacciNumbersMonster with weight: 3], " +
                "[Monster: SumSquareDifferenceMonster with weight: 4], " +
                "[Monster: XthPrimeMonster with weight: 5], " +
                "[Monster: PowerDigitSumMonster with weight: 6], " +
                "[Monster: FactorialMonster with weight: 7], " +
                "[Monster: LongDivisionMonster with weight: 8], " +
                "[Monster: MakeBricksMonster with weight: 9]]",
                Lists.newArrayList(new MonsterLoader().iterator()).toString());
    }
}
