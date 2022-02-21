package com.codenjoy.dojo.questoria.model.items.monster.impl;

import com.codenjoy.dojo.questoria.model.items.monster.MonsterTest;
import com.codenjoy.dojo.questoria.model.items.monster.OneIntCodeRunnerMonster;

public class FibonacciNumbersMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public String method(int number) {
        int previous = 0;
        int current = 0;
        int next = 1;
        for (int index = 0; index < number; index++) {
            previous = current;
            current = next;
            next = previous + current;
        }
        return String.valueOf(current);
    }

    public final static String QUESTION =
            "Сделай метод, возвращающий числа Фибоначчи из \n" +
            "ряда по его номеру:\n" +
            "f(0) = 0\n" +
            "f(1) = 1\n" +
            "f(2) = 0 + 1 = 1\n" +
            "f(3) = 1 + 1 = 2\n" +
            "f(4) = 1 + 2 = 3\n" +
            "f(5) = 2 + 3 = 5\n" +
            "f(6) = 3 + 5 = 8\n" +
            "f(7) = 5 + 8 = 13\n" +
            "f(8) = 8 + 13 = 21\n" +
            "f(9) = 13 + 21 = 34\n" +
            "и так далее...";

    public FibonacciNumbersMonster() {
        super(QUESTION, 3);
    }
}
