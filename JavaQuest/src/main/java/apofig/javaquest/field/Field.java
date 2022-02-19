package apofig.javaquest.field;

import java.util.Arrays;

public class Field {
    private char[][] field;

    private Field() {}

    public Field(int width, int height) {
        field = new char[width][height];
    }

    public Field(int width, int height, char ch) {
        this(width, height);
        fill(field, ch);
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

        return field[x][y];
    }

    public void set(int x, int y, char ch) {
        field[x][y] = ch;
    }

    public int getWidth() {
        return field.length;
    }

    public int getHeight() {
        return field[0].length;
    }

    public FieldPlace get(Point point) {
        return new FieldPlace(this, point.getX(), point.getY());
    }
}
