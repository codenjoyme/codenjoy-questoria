package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.CodeRunnerMonster;
import apofig.javaquest.map.MonsterTest;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 7:10 PM
 */
public class FizzBuzzMonster extends CodeRunnerMonster {

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

    public final static String QUESTION =
            "Write a method “fizbuzz” that has one argument and \n" +
            "returns the string. For multiples of three retunr “Fizz” \n" +
            "instead of the number and for the multiples of five return \n" +
            "“Buzz”. For numbers which are multiples of both three \n" +
            "and five return “FizzBuzz”.";

    public final static String HELP = "Please play with code again!";

    public FizzBuzzMonster(Action onKill) {
        super(QUESTION, new FizzBuzzMonsterTest(), HELP, onKill);
    }
}
