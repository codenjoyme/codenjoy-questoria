package apofig.javaquest.field.object.monster.impl;

import apofig.javaquest.field.object.monster.MonsterTest;
import apofig.javaquest.field.object.monster.OneIntCodeRunnerMonster;

public class FactorialMonster extends OneIntCodeRunnerMonster implements MonsterTest {

    public static final String OK_CODE =
            "    private java.util.Field<Integer, java.math.BigInteger> cache = new java.util.HashMap<Integer, java.math.BigInteger>();\n" +
            "    public String method(int n) {\n" +
            "        java.math.BigInteger result;\n" +
            "        if (n == 0) {\n" +
            "            return \"1\";\n" +
            "        }\n" +
            "        if (null != (result = cache.get(n))) {\n" +
            "            return result.toString();\n" +
            "        }\n" +
            "        result = java.math.BigInteger.valueOf(n).multiply(new java.math.BigInteger(method(n - 1)));\n" +
            "        cache.put(n, result);\n" +
            "        return result.toString();\n" +
            "    }";

    private java.util.Map<Integer, java.math.BigInteger> cache = new java.util.HashMap<Integer, java.math.BigInteger>();
    @Override
    public String method(int n) {
        java.math.BigInteger result;
        if (n == 0) {
            return "1";
        }
        if (null != (result = cache.get(n))) {
            return result.toString();
        }
        result = java.math.BigInteger.valueOf(n).multiply(new java.math.BigInteger(method(n - 1)));
        cache.put(n, result);
        return result.toString();
    }

    public final static String QUESTION =
            "Напиши метод, принимающий один int аргумент и \n" +
            "возвращающий факториал этого числа в виде String.\n" +
            "Внимание! Возможно переполнение int/long.";

    public FactorialMonster() {
        super(QUESTION, 7);
    }


}