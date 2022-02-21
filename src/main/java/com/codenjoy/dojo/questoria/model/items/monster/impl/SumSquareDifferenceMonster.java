package com.codenjoy.dojo.questoria.model.items.monster.impl;

import com.codenjoy.dojo.questoria.model.items.monster.MonsterTest;
import com.codenjoy.dojo.questoria.model.items.monster.OneIntCodeRunnerMonster;

/**
 * @author http://projecteuler.net/problem=6
 */
public class SumSquareDifferenceMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    @Override
    public String method(int input) {
        long sum = 0;
        long sum2 = 0;
        for (int i = 1; i <= input; i++) {
            sum += i;
            sum2 += i * i;
        }
        return Long.toString(sum * sum - sum2);
    }

    public final static String QUESTION =
            "Сумма квадратов первых десяти натуральных чисел\n" +
            "1^2 + 2^2 + ... + 10^2 = 385\n" +
            "\n" +
            "А квадрат суммы - \n" +
            "(1 + 2 + ... + 10)^2 = 55^2 = 3025\n" +
            "\n" +
            "Потому разность между суммой квадратов и \n" +
            "квадратом суммы первых десяти натуральных \n" +
            "чисел равна 3025 − 385 = 2640.\n" +
            "\n" +
            "Создай метод, вычисляющий разность между суммой \n" +
            "квадратов и квадратом суммы для натурального числа i.";

    public SumSquareDifferenceMonster() {
        super(QUESTION, 4);
    }
}
