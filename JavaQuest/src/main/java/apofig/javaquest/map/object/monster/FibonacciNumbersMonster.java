package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 */
public class FibonacciNumbersMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "public String method(int number) {\n" +
            "    int previous = 0;\n" +
            "    int current = 0;\n" +
            "    int next = 1;\n" +
            "    for (int index = 0; index < number; index++) {\n" +
            "        previous = current;\n" +
            "        current = next;\n" +
            "        next = previous + current;\n" +
            "    }\n" +
            "    return String.valueOf(current);\n" +
            "}";

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
            "Сделай метод, возвращающий числ Фибоначчи из \n" +
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
        super(QUESTION);
    }
}
