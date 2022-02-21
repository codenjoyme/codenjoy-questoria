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
 * @author http://euler.jakumo.org/problems/view/7.html
 */
public class XthPrimeMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public java.util.LinkedList<Integer> primes = new java.util.LinkedList<Integer>();
    public String method(int i) {
        if (i == 0) {
            return "1";
        }
        if (i <= primes.size()) {
            return String.valueOf(primes.get(i - 1));
        }
        int index = 1;
        int num = 1;
        if (primes.size() > 0) {
            index = primes.size() + 1;
            num = primes.peekLast();
        }
        while (true) {
            num++;
            boolean cont = false;
            for (int j = 0; j < index - 1; j++) {
                if (num % primes.get(j) == 0) {
                    cont = true;
                    break;
                }
            }
            if (cont) {
                continue;
            }

            if (!new String(new char[num]).matches(".?|(..+?)\\1+")) {
                if (primes.size() < index) {
                    primes.add(num);
                }
                if (i == index) {
                    return String.valueOf(num);
                }
                index++;
            }
        }
    }

    public final static String QUESTION =
            "Первые 10 простых чисел - 2, 3, 5, 7, 11, 13, 17, 19, 23, 29. \n" +
            "Напиши метод, который вернет i-тое простое число.";

    public XthPrimeMonster() {
        super(QUESTION, 5);
    }
}
