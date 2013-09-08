package apofig.javaquest.map.object;

import apofig.javaquest.map.object.impl.Nothing;
import apofig.javaquest.map.object.monster.Monster;
import org.fest.reflect.core.Reflection;
import org.reflections.Reflections;

import java.util.*;

/**
 * User: sanja
 * Date: 06.09.13
 * Time: 3:12
 */
public class ObjectLoader {

    private Map<Character, Class<? extends Something>> cache;

    private final List<? extends Class<? extends Something>> except = Arrays.asList(Monster.class, Me.class, Nothing.class);

    public ObjectLoader() {
        cache = new HashMap<>();

        Reflections reflections = new Reflections(Something.class.getPackage().getName());
        Set<Class<? extends Something>> classes = reflections.getSubTypesOf(Something.class);

        for (Class<? extends Something> clazz : classes) {
            if (skipThis(clazz)) continue;

            Something something = newInstance(clazz);

            cache.put(something.symbol(), clazz);
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
        if (!cache.containsKey(ch)) {
            throw new IllegalArgumentException("Не найден элмент '" + ch + "' в базе объектов.");
        }

        return newInstance(cache.get(ch));
    }
}
