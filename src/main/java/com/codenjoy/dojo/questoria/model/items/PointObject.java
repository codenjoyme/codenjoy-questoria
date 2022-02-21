package com.codenjoy.dojo.questoria.model.items;

import com.codenjoy.dojo.questoria.client.Element;
import com.codenjoy.dojo.questoria.model.Player;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.printer.state.State;

public abstract class PointObject extends PointImpl implements Something, State<Element, Player> {

    public PointObject(Point pt) {
        super(pt);
    }

    public PointObject() {
        this(pt(-1, -1)); // TODO fix me
    }

    @Override
    public boolean equals(Object o) {
        return this == o; // TODO fix me
    }

    @Override
    public char symbol() {
        return state(null).ch();
    }

    @Override
    public abstract Element state(Player player, Object... alsoAtPoint);
}
