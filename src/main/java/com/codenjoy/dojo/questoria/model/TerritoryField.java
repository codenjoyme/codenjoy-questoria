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
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class TerritoryField implements HeroField {

    private int width;
    private int height;
    private Field field;
    private java.util.Map<Viewable, Field> fogs;

    private TerritoryField() {}

    public TerritoryField(FieldLoader loader) {
        width = loader.width();
        height = loader.height();
        field = loader.field();
        fogs = new HashMap<Viewable, Field>();
    }

    @Override
    public FieldPlace newHero(Viewable hero) {
        FieldPlace place = new FieldPlace(field, hero.getX(), hero.getY());
        field.set(hero.getX(), hero.getY(), 'A');

        if (fogs.containsKey(hero)) {
            throw new RuntimeException("Юзер уже проиничен!");
        }
        fogs.put(hero, new Field(width, height, '?'));

        return place;
    }

    @Override
    public void openSpace(Viewable me) {
        final Field fog = fog(me);

        me.view().moveMeTo(me);  // TODO подумать над этим

        me.view().see(me, width, height, new Apply() {
            @Override
            public void xy(int xx, int yy, boolean canSee, boolean isWall) {
                if (canSee && !isWall) {
                    fog.set(xx, yy, ' ');
                }
            }
        });
    }

    private Field fog(Viewable me) {
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
    public String getViewArea(final Viewable me) {
        if (!fogs.containsKey(me)) {
            throw new IllegalArgumentException("Нет такого пользователя!");
        }

        final StringBuffer result = new StringBuffer();

        result.append("╔" + StringUtils.repeat("═", me.view().size()*2) + "╗\n");
        me.view().see(me, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                Point pt = new PointImpl(x, y);
                boolean startLine = x == me.view().getX();
                if (startLine) {
                    result.append("║");
                }

                if (isWall && canSee) {
                    result.append("##");
                } else if (isWall && !canSee) {
                    result.append("??");
                } else if (fog(me).get(x, y) == '?' || field.get(x, y) == '?') {
                    result.append("??");
                } else if (playerAt(pt)) {
                    if (me.isAt(pt)) {
                        result.append("I ");
                    } else {
                        result.append("A ");
                    }
                } else {
                    result.append(field.get(x, y)).append(' ');
                }

                boolean endLine = x == me.view().getX() + me.view().size() - 1;
                if (endLine) {
                    result.append("║\n");
                }
            }
        });
        result.append('╚' + StringUtils.repeat("═", me.view().size()*2) + '╝');

        return result.toString();
    }

    private boolean playerAt(Point point) {
        for (Viewable player : fogs.keySet()) {
            if (player.isAt(point)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void removeHero(Viewable me) {
        field.set(me.getX(), me.getY(), ' ');
        fogs.remove(me);
    }

    public FieldPlace getAt(Point point) {
        return field.get(point);
    }
}
