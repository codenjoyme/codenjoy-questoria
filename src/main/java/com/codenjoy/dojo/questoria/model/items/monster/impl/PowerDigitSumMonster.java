package com.codenjoy.dojo.questoria.model.items.monster.impl;

import com.codenjoy.dojo.questoria.model.items.monster.MonsterTest;
import com.codenjoy.dojo.questoria.model.items.monster.OneIntCodeRunnerMonster;

/**
 * @author http://projecteuler.net/problem=16
 */
public class PowerDigitSumMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public String method(int pow) {
        String temp = java.math.BigInteger.ONE.shiftLeft(pow).toString();
        int sum = 0;
        for (int i = 0; i < temp.length(); i++) {
            sum += temp.charAt(i) - '0';
        }
        return Integer.toString(sum);
    }

    public final static String QUESTION =
            "2^15 = 32768, сумма цифр 3 + 2 + 7 + 6 + 8 = 26.\n" +
            "Какова сумма цифр числа i?\n" +
            "Напиши для расчета метод принимающий int\n" +
            "и возвращающий результат в виде String";

    public PowerDigitSumMonster() {
        super(QUESTION, 6);
    }
}
