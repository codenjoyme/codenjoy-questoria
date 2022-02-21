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

import com.codenjoy.dojo.questoria.model.items.ObjectLoader;
import com.codenjoy.dojo.questoria.model.items.Something;
import org.fest.reflect.core.Reflection;
import org.junit.Test;

import java.util.Comparator;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static junit.framework.Assert.assertEquals;

public class ObjectLoaderTest {

    ObjectLoader loader = new ObjectLoader();

    @Test
    public void shouldGetAllObjects() {
        Map<String, Class<? extends Something>> cache
                = Reflection.field("cache").ofType(java.util.Map.class).in(loader).get();
        cache = cache.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(toMap(java.util.Map.Entry::getKey,
                        java.util.Map.Entry::getValue));
        assertEquals("{" +
                "0=class com.codenjoy.dojo.questoria.model.items.impl.StoneForum, " +
                "#=class com.codenjoy.dojo.questoria.model.items.impl.Wall, " +
                "$=class com.codenjoy.dojo.questoria.model.items.impl.Gold, " +
                "*=class com.codenjoy.dojo.questoria.model.items.impl.drone.Drone, " +
                "M=class com.codenjoy.dojo.questoria.model.items.impl.drone.DroneMentor, " +
                "O=class com.codenjoy.dojo.questoria.model.items.impl.Stone}",
                cache.toString());
    }

    @Test
    public void shouldExceptionIfNotFound() {
        try {
            loader.load('5');
        } catch (IllegalArgumentException e) {
            assertEquals("Не найден элемент '5' в базе объектов.", e.getMessage());
        }
    }
}
