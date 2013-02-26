package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 */
public class PrimeFactoryMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "public String method(int number) {\n" +
            "    String result = \"\";\n" +
            "    if (number == 1) {\n" +
            "        return \"[1]\";\n" +
            "    }\n" +
            "    for (int i = 2; i < number / (i - 1); i++) {\n" +
            "        while (number % i == 0) {\n" +
            "            number /= i;\n" +
            "            result += String.valueOf(i) + \",\";\n" +
            "        }\n" +
            "    }\n" +
            "    if (number > 1) {\n" +
            "        result += String.valueOf(number) + \",\";\n" +
            "    }\n" +
            "    result = result.substring(0, result.length() - 1);\n" +
            "    return \"[\" + result + \"]\";\n" +
            "}";

    public String method(int number) {
        String result = "";
        if (number == 1) {
            return "[1]";
        }
        for (int i = 2; i < number / (i - 1); i++) {
            while (number % i == 0) {
                number /= i;
                result += String.valueOf(i) + ",";
            }
        }
        if (number > 1) {
            result += String.valueOf(number) + ",";
        }
        result = result.substring(0, result.length() - 1);
        return "[" + result + "]";
    }

    public final static String QUESTION =
            "Разложи целое число на множители из простых чисел:\n" +
                    "1 -> [1]\n" +
                    "2 -> [2]\n" +
                    "3 -> [3]\n" +
                    "4 -> [2,2]\n" +
                    "6 -> [2,3]\n" +
                    "9 -> [3,3]\n" +
                    "12 -> [2,2,3]\n" +
                    "15 -> [3,5]\n" +
                    "и так далее...";

    public final static String HELP = "Попробуй еще раз!";

    public PrimeFactoryMonster(Action onKill) {
        super(QUESTION, HELP, onKill);
    }
}
