package apofig.javaquest.map.object.monster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/25/13
 * Time: 8:13 PM
 */
public class SumSquareDifferenceMonsterTest {

    @Test
    public void shouldWork() {
        SumSquareDifferenceMonster monster = new SumSquareDifferenceMonster(null);
        assertEquals("0", monster.method(1));  // 1 - 1 = 0
        assertEquals("4", monster.method(2));  // (1 + 2)^2 - (1^2 + 2^2) = 3^2 - (1+4) = 9 - 5 = 4
        assertEquals("22", monster.method(3));  // (1 + 2 + 3)^2 - (1^2 + 2^2 + 3^2) = 6^2 - (1+4+9) = 36 - 14 = 22
        assertEquals("70", monster.method(4));
        assertEquals("170", monster.method(5));
        assertEquals("350", monster.method(6));
        assertEquals("644", monster.method(7));
        assertEquals("1092", monster.method(8));
        assertEquals("1740", monster.method(9));
        assertEquals("2640", monster.method(10));
        assertEquals("3850", monster.method(11));
        assertEquals("5434", monster.method(12));
        assertEquals("7462", monster.method(13));
        assertEquals("10010", monster.method(14));
        assertEquals("13160", monster.method(15));
        assertEquals("17000", monster.method(16));
        assertEquals("21624", monster.method(17));
        assertEquals("25164150", monster.method(100));
        assertEquals("250166416500", monster.method(1000));
        assertEquals("2500166641665000", monster.method(10000));
    }
}
