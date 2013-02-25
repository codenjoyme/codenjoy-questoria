package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 * @author http://euler.jakumo.org/problems/view/7.html
 */
public class XthPrimeMonster extends CodeRunnerMonster {

    public static final String OK_CODE = "";

    public LinkedList<Integer> primes = new LinkedList<Integer>();

    public String method(int i) {
        if (i < primes.size()) {
            return String.valueOf(primes.get(i));
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

    public final static String HELP = "Попробуй еще раз!";

    public XthPrimeMonster(Action onKill) {
        super(QUESTION, HELP, onKill);
    }
}
