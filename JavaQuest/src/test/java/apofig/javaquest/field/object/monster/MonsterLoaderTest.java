package apofig.javaquest.field.object.monster;

import com.google.common.collect.Lists;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MonsterLoaderTest {

    @Test
    public void test() {
        assertEquals("[[Monster: FizzBuzzMonster with complexity: 1], " +
                "[Monster: PrimeFactoryMonster with complexity: 2], " +
                "[Monster: FibonacciNumbersMonster with complexity: 3], " +
                "[Monster: SumSquareDifferenceMonster with complexity: 4], " +
                "[Monster: XthPrimeMonster with complexity: 5], " +
                "[Monster: PowerDigitSumMonster with complexity: 6], " +
                "[Monster: FactorialMonster with complexity: 7], " +
                "[Monster: LongDivisionMonster with complexity: 8], " +
                "[Monster: MakeBricksMonster with complexity: 9]]",
                Lists.newArrayList(new MonsterLoader().iterator()).toString());
    }
}
