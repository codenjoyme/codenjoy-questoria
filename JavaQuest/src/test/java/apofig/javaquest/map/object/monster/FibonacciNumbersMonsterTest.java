package apofig.javaquest.map.object.monster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 11:42 PM
 */
public class FibonacciNumbersMonsterTest {

    @Test
    public void shouldWork() {
        FibonacciNumbersMonster monster = new FibonacciNumbersMonster(null);
        assertEquals("0", monster.method(0));
        assertEquals("1", monster.method(1));
        assertEquals("1", monster.method(2));
        assertEquals("2", monster.method(3));
        assertEquals("3", monster.method(4));
        assertEquals("5", monster.method(5));
        assertEquals("8", monster.method(6));
        assertEquals("13", monster.method(7));
        assertEquals("21", monster.method(8));
        assertEquals("34", monster.method(9));
        assertEquals("55", monster.method(10));
        assertEquals("89", monster.method(11));
        assertEquals("144", monster.method(12));
        assertEquals("233", monster.method(13));
        assertEquals("377", monster.method(14));
        assertEquals("610", monster.method(15));
        assertEquals("987", monster.method(16));
        assertEquals("1597", monster.method(17));
        assertEquals("2584", monster.method(18));
        assertEquals("4181", monster.method(19));
        assertEquals("6765", monster.method(20));
        assertEquals("10946", monster.method(21));
        assertEquals("17711", monster.method(22));
    }
}
