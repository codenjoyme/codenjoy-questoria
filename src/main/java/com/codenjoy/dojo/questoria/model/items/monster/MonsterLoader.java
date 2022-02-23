package com.codenjoy.dojo.questoria.model.items.monster;

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

import com.codenjoy.dojo.questoria.model.items.Something;
import org.fest.reflect.core.Reflection;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

public class MonsterLoader implements Iterable<Monster> {

    private Map<Class<? extends Monster>, Integer> monsters;

    public MonsterLoader() {
        monsters = new HashMap<>();

        Reflections reflections = new Reflections(Monster.class.getPackage().getName());
        Set<Class<? extends Monster>> classes = reflections.getSubTypesOf(Monster.class);

        for (Class<? extends Monster> clazz : classes) {
            if (skipThis(clazz)) continue;

            Monster monster = newInstance(clazz);

            monsters.put(clazz, monster.getComplexity());
        }
    }

    private boolean skipThis(Class<? extends Something> clazz) {
        if (clazz.getName().contains("Test")) {
            return true;
        }
        return ((clazz.getModifiers() & Modifier.ABSTRACT) != 0);
    }

    private Monster newInstance(Class<? extends Monster> clazz) {
        return Reflection.constructor().in(clazz).newInstance();
    }

    private List<Class<? extends Monster>> sortedList() {
        List<Class<? extends Monster>> result = new LinkedList<>(monsters.keySet());
        Collections.sort(result, Comparator.comparing(o -> monsters.get(o)));
        return result;
    }

    @Override
    public Iterator<Monster> iterator() {
        List<Monster> result = new LinkedList<>();

        for (Class<? extends Monster> clazz : sortedList()) {
            result.add(newInstance(clazz));
        }

        return result.iterator();
    }
}