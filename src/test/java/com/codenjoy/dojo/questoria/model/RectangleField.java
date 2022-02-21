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

public class RectangleField implements FieldLoader {

    private Field field;
    private int posx;
    private int posy;
    private int width;
    private int height;

    public RectangleField(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Field(width, height, ' ');

        posx = 20;
        posy = 20;
    }

    public void setMonster(int x, int y) {
        field.set(x, y, '@');
    }

    public void setWall(int x, int y) {
        field.set(x, y, '#');
    }

    public void setDronMentor(int x, int y) {
        field.set(x, y, 'M');
    }

    public void setGold(int x, int y) {
        field.set(x, y, '$');
    }

    public void setStone(int x, int y) {
        field.set(x, y, 'O');
    }

    public void set(int x, int y, char c) {
        field.set(x, y, c);
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public Field field() {
        return field;
    }

    @Override
    public Point initPosition() {
        return new PointImpl(posx, posy);
    }

    @Override
    public int height() {
        return height;
    }
}
