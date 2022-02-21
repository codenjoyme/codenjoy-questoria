package com.codenjoy.dojo.questoria.model.items;

public interface World {
    Place place();

    void move(int x, int y);

    Something make(char c, Object... params);

}
