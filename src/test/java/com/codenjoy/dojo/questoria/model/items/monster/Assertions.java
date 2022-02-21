package com.codenjoy.dojo.questoria.model.items.monster;

import static junit.framework.Assert.assertEquals;

public class Assertions {
    public static void assertMonster(String[] expected, OneIntCodeRunnerMonster monster) {
        for (int index = 0; index < expected.length; index ++) {
            assertEquals("At index " + index,  expected[index], monster.method(index));
        }
    }

    public static void assertMonster(int[] expected, OneIntCodeRunnerMonster monster) {
        for (int index = 0; index < expected.length; index ++) {
            assertEquals("At index " + index,  String.valueOf(expected[index]), monster.method(index));
        }
    }
}
