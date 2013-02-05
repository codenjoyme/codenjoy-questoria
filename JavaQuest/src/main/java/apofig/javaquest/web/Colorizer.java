package apofig.javaquest.web;

import java.util.HashMap;
import java.util.Map;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 10:05 PM
 */
public class Colorizer {

    private static final Map<String, String> colorizer = new HashMap<String, String>();

    static {
        add("border", "╔", "═", "╗", "║", "╝", "╚");
        add("fog", "?");
        add("iam", "I");
        add("monster", "@");
        add("gold", "$");
        add("wall", "# ");  // TODO
    }

    private static void add(String aclass, String...strings) {
        for (String string : strings) {
            colorizer.put(string, span(aclass));
        }
    }

    private static String span(String aclass) {
        return String.format("<spanclass=\"%s\">%s</span>", aclass, "%s");
    }


    public static String process(String chars) {
        StringBuffer result = new StringBuffer();
        char[] charArray = chars.toCharArray();
        int start = 0;
        int end = chars.length();
//        while (start != charArray.length - 1) {
//            String found = "";
//            for (String string : colorizer.keySet()) {
//                if (chars.substring(start, end).startsWith(string)) {
//                    found = string;
//                    break;
//                }
//            }
//
//
            String c1 = String.valueOf(charArray[start]);
//            char c2 = charArray[end];
//            if (c1 == c2) {
//                continue;
//            }
//
            String span = colorizer.get(c1);
            String line = chars.substring(start, end);
//            if (span == null && !isN(line) && !isS(line)) {
//                throw new IllegalArgumentException("New object in world '" + c1 + "'");
//            }
            processLine(result, span, line);
//
//            start = end;
//        }
//        char lastChar = charArray[start];
//        processLine(result, colorizer.get(lastChar), String.valueOf(lastChar));

        return result.toString().replaceAll("spanclass", "span class");
    }

    private static void processLine(StringBuffer result, String span, String line) {
        if (isN(line)) {
//            result.append(line.replaceAll("\n", "<br>"));
        } else if (isS(line)) {
//            result.append(line.replaceAll(" ", "&nbsp;"));
        } else {
            result.append(String.format(span, line));
        }
    }

    private static boolean isN(String line) {
        return line.charAt(0) == '\n';
    }

    private static boolean isS(String line) {
        return line.charAt(0) == ' ';
    }

}
