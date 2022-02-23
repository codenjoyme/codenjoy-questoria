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

import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.services.Point;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static com.codenjoy.dojo.questoria.client.Element.*;

public class TerritoryField implements HeroField {

    private FieldOld field;
    private java.util.Map<Viewable, FieldOld> fogs;

    private TerritoryField() {}

    public TerritoryField(FieldOld field) {
        this.field = field;
        fogs = new HashMap<>();
    }

    @Override
    public FieldPlace newHero(Viewable hero) {
        FieldPlace place = new FieldPlace(field, hero.getX(), hero.getY());
        field.set(hero.getX(), hero.getY(), OTHER_HERO.ch());

        if (fogs.containsKey(hero)) {
            throw new RuntimeException("Игрок уже существует!");
        }
        fogs.put(hero, new FieldOld(size(), FOG.ch()));

        return place;
    }

    private int size() {
        return field.size();
    }

    @Override
    public void openSpace(Viewable me) {
        FieldOld fog = fog(me);

        me.view().moveMeTo(me);  // TODO подумать над этим

        me.view().see(me, size(), (pt, canSee, isWall) -> {
            if (canSee && !isWall) {
                fog.set(pt, NOTHING.ch());
            }
        });
    }

    private FieldOld fog(Viewable me) {
        return fogs.get(me);
    }

    public void printNear(Me me, OutputStream out) {
        try {
            out.write(getViewArea(me).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getViewArea(Viewable me) {
        if (!fogs.containsKey(me)) {
            throw new IllegalArgumentException("Нет такого пользователя!");
        }

        StringBuffer result = new StringBuffer();
        me.view().see(me, size(), (pt, canSee, isWall) -> {
            result.append(getChar(me, pt, canSee, isWall));

            boolean endLine = pt.getX() == me.view().getX() + me.view().size() - 1;
            if (endLine) {
                result.append("\n");
            }
        });
        return result.toString();
    }

    private char getChar(Viewable me, Point pt, boolean canSee, boolean isWall) {
        if (!canSee) {
            if (fog(me).get(pt) == FOG.ch() || field.get(pt) == FOG.ch()) {
                return FOG.ch();
            }
        }

        if (isWall) {
            if (canSee) {
                return WALL.ch();
            } else {
                return FOG.ch();
            }
        }

        if (playerAt(pt)) {
            if (me.itsMe(pt)) {
                return HERO.ch();
            } else {
                return OTHER_HERO.ch();
            }
        }

        return field.get(pt);
    }

    private boolean playerAt(Point pt) {
        for (Viewable player : fogs.keySet()) {
            if (player.itsMe(pt)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void removeHero(Viewable me) {
        field.set(me.getX(), me.getY(), NOTHING.ch());
        fogs.remove(me);
    }

    public FieldPlace getAt(Point pt) {
        return field.place(pt);
    }
}
