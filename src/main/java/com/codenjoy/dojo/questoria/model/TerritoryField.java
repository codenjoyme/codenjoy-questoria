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
import static com.codenjoy.dojo.services.PointImpl.pt;

public class TerritoryField implements HeroField {

    private int width;
    private int height;
    private FieldOld field;
    private java.util.Map<Viewable, FieldOld> fogs;

    private TerritoryField() {}

    public TerritoryField(FieldLoader loader) {
        width = loader.width();
        height = loader.height();
        field = loader.field();
        fogs = new HashMap<>();
    }

    @Override
    public FieldPlace newHero(Viewable hero) {
        FieldPlace place = new FieldPlace(field, hero.getX(), hero.getY());
        field.set(hero.getX(), hero.getY(), OTHER_HERO.ch());

        if (fogs.containsKey(hero)) {
            throw new RuntimeException("Игрок уже существует!");
        }
        fogs.put(hero, new FieldOld(width, height, FOG.ch()));

        return place;
    }

    @Override
    public void openSpace(Viewable me) {
        final FieldOld fog = fog(me);

        me.view().moveMeTo(me);  // TODO подумать над этим

        me.view().see(me, width, height, (xx, yy, canSee, isWall) -> {
            if (canSee && !isWall) {
                fog.set(xx, yy, NOTHING.ch());
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
        me.view().see(me, width, height, (x, y, canSee, isWall) -> {
            Point pt = pt(x, y);

            if (!canSee && (fog(me).get(x, y) == FOG.ch() || field.get(x, y) == FOG.ch())) {
                result.append(FOG.ch());
            } else if (isWall) {
                if (canSee) {
                    result.append(WALL.ch());
                } else {
                    result.append(FOG.ch());
                }
            } else if (playerAt(pt)) {
                if (me.itsMe(pt)) {
                    result.append(HERO.ch());
                } else {
                    result.append(OTHER_HERO.ch());
                }
            } else {
                result.append(field.get(x, y));
            }

            boolean endLine = x == me.view().getX() + me.view().size() - 1;
            if (endLine) {
                result.append("\n");
            }
        });
        return result.toString();
    }

    private boolean playerAt(Point point) {
        for (Viewable player : fogs.keySet()) {
            if (player.itsMe(point)) {
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

    public FieldPlace getAt(Point point) {
        return field.get(point);
    }
}
