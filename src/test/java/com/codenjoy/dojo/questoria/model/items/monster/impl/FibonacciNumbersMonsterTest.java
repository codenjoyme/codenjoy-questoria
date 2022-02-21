package com.codenjoy.dojo.questoria.model.items.monster.impl;

import com.codenjoy.dojo.questoria.model.items.monster.Assertions;
import org.junit.Test;

public class FibonacciNumbersMonsterTest {

    @Test
    public void shouldWork() {
        int[] expected = new int[]{0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144,
                233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711};
        Assertions.assertMonster(expected, new FibonacciNumbersMonster());
    }
}
