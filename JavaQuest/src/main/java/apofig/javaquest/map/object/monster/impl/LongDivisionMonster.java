package apofig.javaquest.map.object.monster.impl;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.object.monster.ManyInputCodeRunnerMonster;
import apofig.javaquest.map.object.monster.MonsterTest;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 */
public class LongDivisionMonster extends ManyInputCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "    public String method(int a, int b) {\n" +
            "        if (b == 0) {\n" +
            "            return \"Div by zero error!\";\n" +
            "        }\n" +
            "        if (a == 0) {\n" +
            "            return \"0\";\n" +
            "        }\n" +
            "        boolean isMinus = (a < 0 && b > 0 || a > 0 && b < 0);\n" +
            "        if (a < 0) a = -a;\n" +
            "        if (b < 0) b = -b;\n" +
            "\n" +
            "        final int length = 100;\n" +
            "        final int DOT = -1;\n" +
            "        final int TEN = 10;\n" +
            "        final int NOT_FOUND = -1;\n" +
            "\n" +
            "        String result = \"\";\n" +
            "\n" +
            "        int[] aa = new int[length * 2];\n" +
            "        int ia = 0;\n" +
            "        int ceilpos = 0;\n" +
            "\n" +
            "        boolean isCalcCeil = a >= b;\n" +
            "        if (!isCalcCeil) {\n" +
            "            result += \"0.\";\n" +
            "            aa[ia++] = 0;\n" +
            "            aa[ia++] = DOT;\n" +
            "            ceilpos = ia - 1;\n" +
            "        }\n" +
            "        while (a != 0 && (result.length() - ceilpos) <= length) {\n" +
            "            if (a < b) {\n" +
            "                if (isCalcCeil) {\n" +
            "                    result += \".\";\n" +
            "                    aa[ia++] = DOT;\n" +
            "                    ceilpos = ia - 1;\n" +
            "                    isCalcCeil = false;\n" +
            "                }\n" +
            "                a = a * TEN;\n" +
            "            }\n" +
            "            while (a < b) {\n" +
            "                result += \"0\";\n" +
            "                aa[ia++] = 0;\n" +
            "                a = a * TEN;\n" +
            "            }\n" +
            "\n" +
            "            int found = NOT_FOUND;\n" +
            "            for (int ja = 0; ja < ia; ja++) {\n" +
            "                if (aa[ja] == a) {\n" +
            "                    found = ja;\n" +
            "                    break;\n" +
            "                }\n" +
            "            }\n" +
            "            if (found != NOT_FOUND) {\n" +
            "                result = result.substring(0, found) + '(' + result.substring(found) + ')';\n" +
            "                break;\n" +
            "            }\n" +
            "\n" +
            "            int c = a / b;\n" +
            "            if (isCalcCeil) {\n" +
            "                int c1 = c;\n" +
            "                while (c1 / TEN != 0) {\n" +
            "                    aa[ia++] = c1 % TEN;\n" +
            "                    c1 = c1 / TEN;\n" +
            "                }\n" +
            "                aa[ia++] = c1;\n" +
            "            } else {\n" +
            "                aa[ia++] = a;\n" +
            "            }\n" +
            "            int d = a % b;\n" +
            "            result += c;\n" +
            "            a = d;\n" +
            "        }\n" +
            "\n" +
            "        return ((isMinus)?\"-\":\"\") + result;\n" +
            "    }";

    private static final String SIGNATURE =
            "public String method(int a, int b) {\n" +
            "    return |;\n" +
            "}";

    @Override
    public String method(Object... inputs) {
        int a = (Integer) inputs[0];
        int b = (Integer) inputs[1];
        if (b == 0) {
            return "Div by zero error!";
        }
        if (a == 0) {
            return "0";
        }
        boolean isMinus = (a < 0 && b > 0 || a > 0 && b < 0);
        if (a < 0) a = -a;
        if (b < 0) b = -b;

        final int length = 100;
        final int DOT = -1;
        final int TEN = 10;
        final int NOT_FOUND = -1;

        String result = "";

        int[] aa = new int[length * 2];
        int ia = 0;
        int ceilpos = 0;

        boolean isCalcCeil = a >= b;
        if (!isCalcCeil) {
            result += "0.";
            aa[ia++] = 0;
            aa[ia++] = DOT;
            ceilpos = ia - 1;
        }
        while (a != 0 && (result.length() - ceilpos) <= length) {
            if (a < b) {
                if (isCalcCeil) {
                    result += ".";
                    aa[ia++] = DOT;
                    ceilpos = ia - 1;
                    isCalcCeil = false;
                }
                a = a * TEN;
            }
            while (a < b) {
                result += "0";
                aa[ia++] = 0;
                a = a * TEN;
            }

            int found = NOT_FOUND;
            for (int ja = 0; ja < ia; ja++) {
                if (aa[ja] == a) {
                    found = ja;
                    break;
                }
            }
            if (found != NOT_FOUND) {
                result = result.substring(0, found) + '(' + result.substring(found) + ')';
                break;
            }

            int c = a / b;
            if (isCalcCeil) {
                int c1 = c;
                while (c1 / TEN != 0) {
                    aa[ia++] = c1 % TEN;
                    c1 = c1 / TEN;
                }
                aa[ia++] = c1;
            } else {
                aa[ia++] = a;
            }
            int d = a % b;
            result += c;
            a = d;
        }

        return ((isMinus)?"-":"") + result;
    }

    @Override
    protected Object[][] getTestData() {
        return new Object[][]{
                new Object[]{1, 2},
                new Object[]{1, 1},
                new Object[]{5, 5},
                new Object[]{55, 5},
                new Object[]{55, 44},
                new Object[]{0, 56},
                new Object[]{56, 1},
                new Object[]{1, -2},
                new Object[]{-1, 2},
                new Object[]{-1, -2},
                new Object[]{1, 1000},
                new Object[]{56, 45},
                new Object[]{111, 110},
                new Object[]{111, 11},
                new Object[]{11111, 11},
                new Object[]{-11, -222},
                new Object[]{111, -22},
                new Object[]{1, 3000},
                new Object[]{87, 78},
                new Object[]{45, 56},
                new Object[]{212, 133},
                new Object[]{11111, 115},
                new Object[]{123, 345},
                new Object[]{66666666, 77727777},
                new Object[]{666666660, 77727777},
                new Object[]{666666660, 7772777},
                new Object[]{100, 97},
                new Object[]{999, 0},
        };
    }

    public final static String QUESTION =
            "Напиши метод, который делит два числа в столбик \n" +
                    "с точностью до 100 знака.\n" +
                    "Если в результате встречается период, " +
                    "его стоит записать так\n" +
                    "212/133 = 1.(593984962406015037)\n" +
                    "87/78   = 1.1(153846)";

    public LongDivisionMonster() {
        super(QUESTION, SIGNATURE, 8);
    }
}
