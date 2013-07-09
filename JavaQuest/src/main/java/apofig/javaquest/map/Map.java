package apofig.javaquest.map;

import apofig.javaquest.map.MapLoader;

import java.util.Arrays;

/**
 * User: sanja
 * Date: 09.07.13
 * Time: 22:56
 */
public class Map {
    private char[][] map;

    public Map(int width, int height) {
        map = new char[width][height];
    }

    public Map(int width, int height, char ch) {
        this(width, height);
        fill(map, ch);
    }

    public static void fill(char[][] m, char с) {
        for (int x = 0; x < m.length; x++) {
            Arrays.fill(m[x], с);
        }
    }

    public char get(int x, int y) {
        return map[x][y];
    }

    public void set(int x, int y, char ch) {
        map[x][y] = ch;
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }
}
