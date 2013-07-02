package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 * @author http://euler.jakumo.org/problems/view/7.html
 */
public class XthPrimeMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "    public java.util.LinkedList<Integer> primes = new java.util.LinkedList<Integer>();\n" +
            "    public String method(int i) {\n" +
            "        if (i == 0) {\n" +
            "            return \"1\";\n" +
            "        }" +
            "        if (i < primes.size()) {\n" +
            "            return String.valueOf(primes.get(i));\n" +
            "        }\n" +
            "        int index = 1;\n" +
            "        int num = 1;\n" +
            "        if (primes.size() > 0) {\n" +
            "            index = primes.size() + 1;\n" +
            "            num = primes.peekLast();\n" +
            "        }\n" +
            "        while (true) {\n" +
            "            num++;\n" +
            "            boolean cont = false;\n" +
            "            for (int j = 0; j < index - 1; j++) {\n" +
            "                if (num % primes.get(j) == 0) {\n" +
            "                    cont = true;\n" +
            "                    break;\n" +
            "                }\n" +
            "            }\n" +
            "            if (cont) {\n" +
            "                continue;\n" +
            "            }\n" +
            "            if (!new String(new char[num]).matches(\".?|(..+?)\\\\1+\")) {\n" +
            "                if (primes.size() < index) {\n" +
            "                    primes.add(num);\n" +
            "                }\n" +
            "                if (i == index) {\n" +
            "                    return String.valueOf(num);\n" +
            "                }\n" +
            "                index++;\n" +
            "            }\n" +
            "        }\n" +
            "    }";

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

    public XthPrimeMonster(Action onKill) {
        super(QUESTION, onKill);
    }
}
