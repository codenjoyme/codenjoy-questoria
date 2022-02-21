package com.codenjoy.dojo.questoria.model.items.monster.impl;

import com.codenjoy.dojo.questoria.model.items.monster.Assertions;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SumSquareDifferenceMonsterTest {

    @Test
    public void shouldWork() {
        int[] primes = new int[] {0, 0, 4, 22, 70, 170, 350, 644, 1092, 1740, 2640, 3850, 5434, 7462, 10010, 13160, 17000, 21624};
        SumSquareDifferenceMonster monster = new SumSquareDifferenceMonster();
        Assertions.assertMonster(primes, monster);
        assertEquals("25164150", monster.method(100));
        assertEquals("250166416500", monster.method(1000));
        assertEquals("2500166641665000", monster.method(10000));
    }
}
