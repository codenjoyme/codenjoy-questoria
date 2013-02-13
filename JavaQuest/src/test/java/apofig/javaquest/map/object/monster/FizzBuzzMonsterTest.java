package apofig.javaquest.map.object.monster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 11:42 PM
 */
public class FizzBuzzMonsterTest {

    @Test
    public void shouldWork() {
        FizzBuzzMonster monster = new FizzBuzzMonster(null);
        assertEquals("1", monster.method(1));
        assertEquals("2", monster.method(2));
        assertEquals("Fizz", monster.method(3));
        assertEquals("4", monster.method(4));
        assertEquals("Buzz", monster.method(5));
        assertEquals("Fizz", monster.method(6));
        assertEquals("7", monster.method(7));
        assertEquals("8", monster.method(8));
        assertEquals("Fizz", monster.method(9));
        assertEquals("Buzz", monster.method(10));
        assertEquals("11", monster.method(11));
        assertEquals("Fizz", monster.method(12));
        assertEquals("13", monster.method(13));
        assertEquals("14", monster.method(14));
        assertEquals("FizzBuzz", monster.method(15));
        assertEquals("16", monster.method(16));
        assertEquals("17", monster.method(17));
        assertEquals("Fizz", monster.method(18));
        assertEquals("19", monster.method(19));
        assertEquals("Buzz", monster.method(20));
        assertEquals("Fizz", monster.method(21));
        assertEquals("22", monster.method(22));
        assertEquals("23", monster.method(23));
        assertEquals("Fizz", monster.method(24));
        assertEquals("Buzz", monster.method(25));
        assertEquals("26", monster.method(26));
    }
}
