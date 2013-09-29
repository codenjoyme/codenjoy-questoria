package apofig.javaquest.map.object.monster.impl;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.object.monster.ManyInputCodeRunnerMonster;
import apofig.javaquest.map.object.monster.MonsterTest;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 19:11
 */
public class MakeBricksMonster extends ManyInputCodeRunnerMonster implements MonsterTest {

    private static final String SIGNATURE =
            "public boolean method(int small, int big, int goal) {\n" +
            "    return |;\n" +
            "}";

    public static final String OK_CODE =
            "public boolean method(int small, int big, int goal) {\n" +
            "    return (small >= (goal % 5) && small >= goal - big * 5);\n" +
            "}";

    public MakeBricksMonster() {
        super(QUESTION, SIGNATURE, 9);
    }

    @Override
    protected String method(Object... inputs) {
        int small = (Integer)inputs[0];
        int big = (Integer)inputs[1];
        int goal = (Integer)inputs[2];

        boolean result = (small >= (goal % 5) && small >= goal - big * 5);

        return String.valueOf(result);
    }

    @Override
    protected Object[][] getTestData() {
        return new Object[][]{
                new Object[]{0, 1, 5},
                new Object[]{1, 0, 1},
                new Object[]{1, 0, 2},
                new Object[]{3, 1, 7},
                new Object[]{4, 2, 14},
                new Object[]{3, 2, 14},
                new Object[]{0, 2, 5},
                new Object[]{3, 1, 6},
                new Object[]{3, 1, 9},
                new Object[]{3, 2, 9},
                new Object[]{10, 10, 0},
                new Object[]{0, 0, 1},
                new Object[]{0, 1, 1},
                new Object[]{0, 0, 2},
                new Object[]{1, 0, 2},
                new Object[]{2, 0, 2},
                new Object[]{0, 1, 2},
                new Object[]{1, 1, 2},
                new Object[]{2, 1, 3},
                new Object[]{3, 1, 4},
                new Object[]{2, 1, 1},
                new Object[]{2, 2, 6},
                new Object[]{6, 1, 11},
                new Object[]{5, 0, 1},
                new Object[]{3, 1, 8},
                new Object[]{3, 1, 9},
                new Object[]{3, 2, 10},
                new Object[]{3, 2, 8},
                new Object[]{3, 2, 9},
                new Object[]{6, 1, 11},
                new Object[]{6, 0, 11},
                new Object[]{1, 4, 11},
                new Object[]{0, 3, 10},
                new Object[]{1, 4, 12},
                new Object[]{3, 1, 7},
                new Object[]{1, 1, 7},
                new Object[]{2, 1, 7},
                new Object[]{7, 1, 11},
                new Object[]{7, 1, 8},
                new Object[]{7, 1, 13},
                new Object[]{43, 1, 46},
                new Object[]{40, 1, 46},
                new Object[]{40, 2, 47},
                new Object[]{40, 2, 50},
                new Object[]{40, 2, 52},
                new Object[]{22, 2, 33},
                new Object[]{0, 2, 10},
                new Object[]{1000000, 1000, 1000100},
                new Object[]{2, 1000000, 100003},
                new Object[]{20, 0, 19},
                new Object[]{20, 0, 21},
                new Object[]{20, 4, 51},
                new Object[]{20, 4, 39},
        };
    }

    public final static String QUESTION =
            "У тебя есть кирпичики размером 5 (bigCount) и 1 (smallCount). \n" +
            "Напиши метод, который смог бы определить получится из заданного \n" +
            "набора кирпичиков  построить стенку заданной длинны (length). \n" +
            "Например: \n" +
            "method(3, 1, 8) → true\n" +
            "method(3, 1, 9) → false\n" +
            "method(3, 2, 10) → true\n" +
            "(задача взята с http://codingbat.com/prob/p183562)";
}
