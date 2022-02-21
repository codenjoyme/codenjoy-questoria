package com.codenjoy.dojo.questoria.model.items.monster.impl;

import com.codenjoy.dojo.questoria.model.items.monster.Assertions;
import org.junit.Test;

public class PowerDigitSumMonsterTest {

    @Test
    public void shouldWork() {
        int[] primes = new int[] {1, 2, 4, 8, 7, 5, 10, 11, 13, 8, 7, 14, 19, 20, 22, 26, 25, 14, 19, 29, 31, 26, 25};
        Assertions.assertMonster(primes, new PowerDigitSumMonster());
    }


}
