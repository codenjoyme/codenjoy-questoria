package com.codenjoy.dojo.questoria.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
