package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.object.Something;
import org.fest.reflect.core.Reflection;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * User: sanja
 * Date: 06.09.13
 * Time: 3:12
 */
public class MonsterLoader implements Iterable<Monster> {

    private Map<Class<? extends Monster>, Integer> monsters;

    public MonsterLoader() {
        monsters = new HashMap<Class<? extends  Monster>, Integer>();

        Reflections reflections = new Reflections(Monster.class.getPackage().getName());
        Set<Class<? extends Monster>> classes = reflections.getSubTypesOf(Monster.class);

        for (Class<? extends Monster> clazz : classes) {
            if (skipThis(clazz)) continue;

            Monster monster = newInstance(clazz);

            monsters.put(clazz, monster.getComplexity());
        }
    }

    private boolean skipThis(Class<? extends Something> clazz) {
        return ((clazz.getModifiers() & Modifier.ABSTRACT) != 0);
    }

    private Monster newInstance(Class<? extends Monster> clazz) {
        return Reflection.constructor().in(clazz).newInstance();
    }

    private List<Class<? extends Monster>> sortedList() {
        List<Class<? extends Monster>> result = new LinkedList<Class<? extends Monster>>(monsters.keySet());

        Collections.sort(result, new Comparator<Class<? extends Monster>>() {
            @Override
            public int compare(Class<? extends Monster> o1, Class<? extends Monster> o2) {
                return monsters.get(o1).compareTo(monsters.get(o2));
            }
        });

        return result;
    }

    @Override
    public Iterator<Monster> iterator() {
        List<Monster> result = new LinkedList<Monster>();

        for (Class<? extends Monster> clazz : sortedList()) {
            result.add(newInstance(clazz));
        }

        return result.iterator();
    }
}
