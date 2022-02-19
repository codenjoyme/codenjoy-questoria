package apofig.javaquest.field.object.monster.impl;

import apofig.javaquest.field.object.monster.MonsterTest;
import apofig.javaquest.field.object.monster.OneIntCodeRunnerMonster;

public class PrimeFactoryMonster extends OneIntCodeRunnerMonster implements MonsterTest {

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

    public PrimeFactoryMonster() {
        super(QUESTION, 2);
    }
}
