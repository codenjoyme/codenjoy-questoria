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

import com.codenjoy.dojo.questoria.model.items.impl.Nothing;
import com.codenjoy.dojo.questoria.model.items.monster.Monster;
import org.fest.reflect.core.Reflection;
import org.reflections.Reflections;

import java.util.*;

public class ObjectLoader {

    private Map<String, Class<? extends Something>> cache;

    private final List<? extends Class<? extends Something>> except = Arrays.asList(Monster.class, Me.class, Nothing.class);

    public ObjectLoader() {
        cache = new HashMap<String, Class<? extends Something>>();

        Reflections reflections = new Reflections(Something.class.getPackage().getName());
        Set<Class<? extends Something>> classes = reflections.getSubTypesOf(Something.class);

        for (Class<? extends Something> clazz : classes) {
            if (skipThis(clazz)) continue;

            Something something = newInstance(clazz);

            cache.put(String.valueOf(something.symbol()), clazz);
        }
    }

    private boolean skipThis(Class<? extends Something> clazz) {
        for (Class<? extends Something> c : except) {
            if (c.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    private Something newInstance(Class<? extends Something> clazz) {
        return Reflection.constructor().in(clazz).newInstance();
    }

    public Something load(char ch) {
        String key = String.valueOf(ch);
        if (!cache.containsKey(key)) {
            throw new IllegalArgumentException("Не найден элмент '" + ch + "' в базе объектов.");
        }

        return newInstance(cache.get(key));
    }

    public List<String> objects() {
        return new LinkedList(cache.keySet());
    }
}
