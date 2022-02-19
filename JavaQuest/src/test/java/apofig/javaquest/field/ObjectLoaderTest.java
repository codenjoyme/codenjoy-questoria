package apofig.javaquest.field;

import apofig.javaquest.field.object.ObjectLoader;
import apofig.javaquest.field.object.Something;
import org.fest.reflect.core.Reflection;
import org.junit.Test;

import java.util.*;
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
        assertEquals("{0=class apofig.javaquest.field.object.impl.StoneForum, " +
                "#=class apofig.javaquest.field.object.impl.Wall, " +
                "$=class apofig.javaquest.field.object.impl.Gold, " +
                "*=class apofig.javaquest.field.object.impl.dron.Dron, " +
                "M=class apofig.javaquest.field.object.impl.dron.DronMentor, " +
                "O=class apofig.javaquest.field.object.impl.Stone}", cache.toString());
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
