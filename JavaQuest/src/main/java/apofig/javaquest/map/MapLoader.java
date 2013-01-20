package apofig.javaquest.map;

import java.util.Arrays;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:05 PM
 */
public interface MapLoader {
    int getMapSize();

    char[][] getMap();

    char[][] getFog();

    int getPlayerX();

    int getPlayerY();

    static class Utils {
        public static void fill(char[][] m, char с) {
            for (int x = 0; x < m.length; x++) {
                Arrays.fill(m[x], с);
            }
        }
    }

}
