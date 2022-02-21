package com.codenjoy.dojo.questoria.model;

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
        java.util.Map<String, Class<? extends Something>> cache
                = Reflection.field("cache").ofType(java.util.Map.class).in(loader).get();
        cache = cache.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(toMap(java.util.Map.Entry::getKey,
                        java.util.Map.Entry::getValue));
        assertEquals("{0=class com.codenjoy.dojo.questoria.model.items.impl.StoneForum, " +
                "#=class com.codenjoy.dojo.questoria.model.items.impl.Wall, " +
                "$=class com.codenjoy.dojo.questoria.model.items.impl.Gold, " +
                "*=class com.codenjoy.dojo.questoria.model.items.impl.dron.Dron, " +
                "M=class com.codenjoy.dojo.questoria.model.items.impl.dron.DronMentor, " +
                "O=class com.codenjoy.dojo.questoria.model.items.impl.Stone}", cache.toString());
    }

    @Test
    public void shouldExceptionIfNotFound() {
        try {
            loader.load('5');
        } catch (IllegalArgumentException e) {
            assertEquals("Не найден элмент '5' в базе объектов.", e.getMessage());
        }
    }
}
