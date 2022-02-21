package com.codenjoy.dojo.questoria.model.items;

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

public class WorldImpl implements World {

    private Place place;
    private String name;
    private ObjectFactory factory;
    private Me founder;

    private WorldImpl() {}

    public WorldImpl(ObjectFactory factory, Place place, Something object, Me founder) {
        this.factory = factory;
        this.place = place;
        this.founder = founder;
        this.name = object.getClass().getSimpleName();
    }

    @Override
    public Something make(char c, Object... params) {
        place.update(c);
        // TODO подумать над этим - некрасиво как-то
        Object parameter = (params != null && params.length > 0) ? params[0] : null;
        return factory.get(place, founder, parameter);
    }

    @Override
    public void move(int x, int y) {
        char ch = place.getChar();
        place.update(' ');
        place.move(x, y);
        place.update(ch);
    }

    @Override
    public String toString() {
        return String.format("[object %s at %s]", name, place);
    }

    @Override
    public Place place() {
        return place;
    }

}
