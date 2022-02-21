package com.codenjoy.dojo.questoria.model.items;

import com.codenjoy.dojo.questoria.model.Point;

import java.util.List;

public interface Place extends Point {
    void update(char newChar);

    char getChar();

    List<Place> near();

    Place near(int dx, int dy);

    void move(int x, int y);
}
