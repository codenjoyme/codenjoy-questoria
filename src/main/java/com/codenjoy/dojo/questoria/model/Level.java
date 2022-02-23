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

import com.codenjoy.dojo.questoria.model.items.impl.Gold;
import com.codenjoy.dojo.questoria.model.items.impl.Wall;
import com.codenjoy.dojo.services.field.AbstractLevel;
import com.codenjoy.dojo.services.field.PointField;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.codenjoy.dojo.questoria.client.Element.*;

public class Level extends AbstractLevel {

    public Level() {
        super(StringUtils.EMPTY);
    }

    public Level(String map) {
        super(map);
    }

    public List<Hero> heroes() {
        return find(Hero::new, HERO);
    }

    public List<Gold> gold() {
        return find(Gold::new, GOLD);
    }

    public List<Wall> walls() {
        return find(Wall::new, WALL);
    }

    @Override
    protected void fill(PointField field) {
        field.addAll(walls());
        field.addAll(gold());
    }
}