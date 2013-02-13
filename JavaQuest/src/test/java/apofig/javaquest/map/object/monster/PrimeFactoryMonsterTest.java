package apofig.javaquest.map.object.monster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 11:42 PM
 */
public class PrimeFactoryMonsterTest {

    @Test
    public void shouldWork() {
        PrimeFactoryMonster monster = new PrimeFactoryMonster(null);
        assertEquals("[1]", monster.method(1));
        assertEquals("[2]", monster.method(2));
        assertEquals("[3]", monster.method(3));
        assertEquals("[2,2]", monster.method(4));
        assertEquals("[5]", monster.method(5));
        assertEquals("[2,3]", monster.method(6));
        assertEquals("[7]", monster.method(7));
        assertEquals("[2,2,2]", monster.method(8));
        assertEquals("[3,3]", monster.method(9));
        assertEquals("[2,5]", monster.method(10));
        assertEquals("[11]", monster.method(11));
        assertEquals("[2,2,3]", monster.method(12));
        assertEquals("[13]", monster.method(13));
        assertEquals("[2,7]", monster.method(14));
        assertEquals("[3,5]", monster.method(15));
        assertEquals("[2,2,2,2]", monster.method(16));
        assertEquals("[17]", monster.method(17));
        assertEquals("[2,3,3]", monster.method(18));
        assertEquals("[19]", monster.method(19));
        assertEquals("[2,2,5]", monster.method(20));
        assertEquals("[3,7]", monster.method(21));
        assertEquals("[2,11]", monster.method(22));
        assertEquals("[23]", monster.method(23));
        assertEquals("[2,2,2,3]", monster.method(24));
        assertEquals("[5,5]", monster.method(25));
        assertEquals("[2,13]", monster.method(26));
    }
}
