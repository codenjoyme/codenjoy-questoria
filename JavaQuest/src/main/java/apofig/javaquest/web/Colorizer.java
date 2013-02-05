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
        int end = 1;
        while (end != charArray.length) {
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
            String c2 = String.valueOf(charArray[end]);
            if (c1.equals(c2)) {
                end++;
                if (end <= charArray.length - 1) {
                    continue;
                }
            }

            String line = chars.substring(start, end);
            String span = colorizer.get(c1);
//            if (span == null && !isN(line) && !isS(line)) {
//                throw new IllegalArgumentException("New object in world '" + c1 + "'");
//            }
            processLine(result, span, line);
//
            start = end;
        }

        addFogSpan(result);

        return result.toString().replaceAll("spanclass", "span class");
    }

    private static void addFogSpan(StringBuffer result) {
        result.insert(0, "<span class=\"fog\">");
        result.insert(result.length(), "</span>");
    }

    private static void processLine(StringBuffer result, String span, String line) {
        if (isFog(line)) {
            result.append(line);
        } else if (isN(line)) {
            result.append(line.replaceAll("\n", "<br>"));
        } else if (isS(line)) {
            result.append(line.replaceAll(" ", "&nbsp;"));
        } else {
            result.append(String.format(span, line));
        }
    }

    private static boolean isFog(String line) {
        return line.charAt(0) == '?';
    }

    private static boolean isN(String line) {
        return line.charAt(0) == '\n';
    }

    private static boolean isS(String line) {
        return line.charAt(0) == ' ';
    }

}
