package apofig.javaquest.map;

import java.util.Arrays;

/**
 * User: sanja
 * Date: 09.07.13
 * Time: 22:56
 */
public class Map {
    private char[][] map;

    private Map() {}

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

    private boolean isOutOfWorld(int x, int y) {
        return x < 0 || y < 0 || y >= getHeight() || x >= getWidth();
    }

    public char get(int x, int y) {
        if (isOutOfWorld(x, y)) return '#';

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

    public MapPlace get(Point point) {
        return new MapPlace(this, point.getX(), point.getY());
    }
}
