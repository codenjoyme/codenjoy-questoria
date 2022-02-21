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
