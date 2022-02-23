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

import com.codenjoy.dojo.services.Point;

import java.util.Arrays;

import static com.codenjoy.dojo.questoria.client.Element.WALL;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class FieldOld {

    private char[][] field;

    private FieldOld() {}

    public FieldOld(int size) {
        field = new char[size][size];
    }

    public FieldOld(int size, char ch) {
        this(size);
        fill(field, ch);
    }

    public static void fill(char[][] m, char с) {
        for (int x = 0; x < m.length; x++) {
            Arrays.fill(m[x], с);
        }
    }

    public char get(int x, int y) {
        return get(pt(x, y));
    }

    public char get(Point pt) {
        if (pt.isOutOf(size())) return WALL.ch();

        return field[pt.getX()][pt.getY()];
    }

    public void set(Point pt, char ch) {
        set(pt.getX(), pt.getY(), ch);
    }

    public void set(int x, int y, char ch) {
        field[x][y] = ch;
    }

    public int size() {
        return field.length;
    }

    public FieldPlace place(Point pt) {
        return new FieldPlace(this, pt.getX(), pt.getY());
    }
}
