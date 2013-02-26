package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 */
public class FizzBuzzMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "public String fizzbuzz(int i) {" +
            "   String result = \"\";" +
            "   if (i % 3 == 0) {" +
            "       result += \"Fizz\";" +
            "   }" +
            "   if (i % 5 == 0) {" +
            "       result += \"Buzz\";" +
            "   }" +
            "   if (result.length() == 0) {" +
            "       result = String.valueOf(i);" +
            "   }" +
            "   return result;" +
            "}";

    public String method(int i) {
       String result = "";
       if (i % 3 == 0) {
           result += "Fizz";
       }
       if (i % 5 == 0) {
           result += "Buzz";
       }
       if (result.length() == 0) {
           result = String.valueOf(i);
       }
       return result;
    }

    public final static String QUESTION =
            "Напиши метод, принимающий один int аргумент и \n" +
            "возвращающий String. Для тех чисел, которые делятся \n" +
            "нацело на 3 метод должен вернуть “Fizz”, для тех, \n" +
            "что делятся на 5 - “Buzz”, для тех же, что делятся и на 3 \n" +
            "и на 5 - “FizzBuzz”, ну а для всех остальных - само число.";

    public final static String HELP = "Попробуй еще раз!";

    public FizzBuzzMonster(Action onKill) {
        super(QUESTION, HELP, onKill);
    }


}
