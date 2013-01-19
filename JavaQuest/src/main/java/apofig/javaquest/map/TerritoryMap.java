package apofig.javaquest.map;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:13 PM
 */
public class TerritoryMap {
    public void printNearMe(OutputStream out) {
        try {
            out.write(
                    ("?????????????\n" +
                    "?????   ?????\n" +
                    "???       ???\n" +
                    "??         ??\n" +
                    "??         ??\n" +
                    "?           ?\n" +
                    "?     I     ?\n" +
                    "?           ?\n" +
                    "??         ??\n" +
                    "??         ??\n" +
                    "???       ???\n" +
                    "?????   ?????\n" +
                    "?????????????").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
