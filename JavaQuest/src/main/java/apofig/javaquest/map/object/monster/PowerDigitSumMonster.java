package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;


/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 * @author http://projecteuler.net/problem=16
 */
public class PowerDigitSumMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "public String method(int pow) {\n" +
            "        String temp = java.math.BigInteger.ONE.shiftLeft(pow).toString();\n" +
            "        int sum = 0;\n" +
            "        for (int i = 0; i < temp.length(); i++) {\n" +
            "            sum += temp.charAt(i) - '0';\n" +
            "        }\n" +
            "        return Integer.toString(sum);\n" +
            "    }";

    public String method(int pow) {
        String temp = java.math.BigInteger.ONE.shiftLeft(pow).toString();
        int sum = 0;
        for (int i = 0; i < temp.length(); i++) {
            sum += temp.charAt(i) - '0';
        }
        return Integer.toString(sum);
    }

    public final static String QUESTION =
            "215 = 32768, сумма цифр 3 + 2 + 7 + 6 + 8 = 26.\n" +
            "Какова сумма цифр числа i?\n" +
            "Напиши для расчета метод принимающий int\n" +
            "и возвращающий результат в виде String";

    public final static String HELP = "Попробуй еще раз!";

    public PowerDigitSumMonster(Action onKill) {
        super(QUESTION, HELP, onKill);
    }
}
