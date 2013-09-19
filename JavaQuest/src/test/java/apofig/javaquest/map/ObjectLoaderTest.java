package apofig.javaquest.map;

import apofig.javaquest.map.object.ObjectLoader;
import org.fest.reflect.core.Reflection;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: sanja
 * Date: 06.09.13
 * Time: 3:27
 */
public class ObjectLoaderTest {

    ObjectLoader loader = new ObjectLoader();

    @Test
    public void shouldGetAllObjects() {
        java.util.Map cache = Reflection.field("cache").ofType(java.util.Map.class).in(loader).get();
        assertEquals("{#=class apofig.javaquest.map.object.impl.Wall, " +
                "$=class apofig.javaquest.map.object.impl.Gold, " +
                "*=class apofig.javaquest.map.object.impl.dron.Dron, " +
                "M=class apofig.javaquest.map.object.impl.dron.DronMentor, " +
                "O=class apofig.javaquest.map.object.monster.Stone}", cache.toString());
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
