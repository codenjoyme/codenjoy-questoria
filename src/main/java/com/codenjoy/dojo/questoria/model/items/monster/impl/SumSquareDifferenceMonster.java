package com.codenjoy.dojo.questoria.model.items.monster.impl;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
