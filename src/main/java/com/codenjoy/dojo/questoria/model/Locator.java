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
import com.codenjoy.dojo.questoria.model.items.ObjectFactory;
import com.codenjoy.dojo.questoria.model.items.Place;
import com.codenjoy.dojo.questoria.model.items.Something;
import com.codenjoy.dojo.services.Point;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.services.PointImpl.pt;

public class Locator implements FieldLocator {

    private TerritoryField field;
    private ObjectFactory factory;

    private Locator() {}

    public Locator(TerritoryField field, ObjectFactory factory) {
        this.field = field;
        this.factory = factory;
    }

    @Override
    public List<Something> getNear(Me founder) {
        List<Place> near = field.getAt(pt(founder.getX(), founder.getY())).near();
        List<Something> result = new LinkedList<Something>();
        for (Place place : near) {
            if (place.getChar() == ' ') continue;
            result.add(get(founder, place));
        }
        return result;
    }

    private Something get(Me founder, Place place) {
        // TODO продумать как локатор будет конфигурировать объекты, как это сейчас делает ObjectFactory
        return factory.get(place, founder, null);
    }

    @Override
    public Something getAt(Point point, Me founder) {
        return get(founder, field.getAt(point));
    }

    @Override
    public boolean isNear(Me founder, Something object) {
        return getNear(founder).contains(object);
    }
}
