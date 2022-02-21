package com.codenjoy.dojo.questoria.model;

import com.codenjoy.dojo.questoria.model.items.Place;

import java.util.LinkedList;
import java.util.List;

public class FieldPlace implements Place {
    private Field field;
    private int x;
    private int y;

    private FieldPlace() {}

    public FieldPlace(Field field, int x, int y) {
        this.field = field;
        this.x = x;
        this.y = y;
    }

    public void update(char newChar) {
        field.set(x, y, newChar);
    }

    @Override
    public char getChar() {
        return field.get(x, y);
    }

    @Override
    public List<Place> near() {
        List<Place> result = new LinkedList<Place>();

        result.add(near( 0, -1));

        result.add(near( 1, 0));

        result.add(near( 0, 1));

        result.add(near(-1, 0));

        return result;
    }

    @Override
    public Place near(int dx, int dy) {
        return new FieldPlace(field, x + dx, y + dy);
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public boolean isAt(Point point) {
        return x == point.getX() && y == point.getY();
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("field[%s,%s]='%s'", x, y, getChar());
    }

}
