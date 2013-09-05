package apofig.javaquest.map.object;

import apofig.javaquest.map.Dieble;
import apofig.javaquest.map.object.dron.Dron;
import apofig.javaquest.map.object.dron.DronMentor;
import apofig.javaquest.map.object.monster.MonsterPool;
import apofig.javaquest.services.Tickable;
import org.fest.reflect.core.Reflection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:52 PM
 */
public class ObjectFactoryImpl implements ObjectFactory {

    private MonsterPool monsters;
    private List<Something> objects;

    public ObjectFactoryImpl(MonsterPool monsters) {
        this.monsters = monsters;
        objects = new LinkedList<Something>();
    }

    @Override
    public Something get(Place place) {
        for (Something smth : objects) {
            if (smth.isAt(place)) {
                if (smth.symbol() != place.getChar()) {
                    killSomething(smth);
                } else {
                    return smth;
                }
            }
        }

        ObjectSettings object = getObject(place.getChar());
        object.setWorld(new WorldImpl(this, place, object));

        Something smth = (Something)object;

        if (!(smth instanceof Nothing)) {
            objects.add(smth);
        }
        return smth;
    }

    private void killSomething(Something smth) {
        objects.remove(smth);
        if (Dieble.class.isAssignableFrom(smth.getClass())) {
            ((Dieble)smth).die();
        }
    }

    @Override
    public void add(Me me) {
        objects.add(me);
    }

    private ObjectSettings getObject(char c) {
        if (c == ' ' || c == 'I') {
            return new Nothing();
        } else if (c == 'A') {
//            return new Alien();
            throw new IllegalStateException("Незареганный Alien!!!");
        } else if (c == '@') {
            return monsters.next();
        } else if (c == '#') {
            return new Wall();
        } else if (c == '$') {
            return new Gold();
        } else if (c == 'M') {
            return new DronMentor();
        } else if (c == '*') {
            return new Dron();
        }
        throw newObjectError(""+c);
    }

    public static UnsupportedOperationException newObjectError(String c) {
        throw new UnsupportedOperationException("WTF! Новый объект в мире, а мы не в курсе: '" + c + "'");
    }

    @Override
    public String toString() {  // TODO для целей тстирования - найти способ удалить!
        List<String> result = new ArrayList<>();
        for (Something smth : objects) {
            result.add(Reflection.field("world").ofType(World.class).in(smth).get().toString());
        }
        return result.toString();
    }

    /**
     * Любой объект мира (Something) маркированный как Tickable будет каждый тик оповещаться от JavaQuest
     */
    @Override
    public void tick() {
        for (Something smth : objects) {
            if (Tickable.class.isAssignableFrom(smth.getClass())) {
                ((Tickable)smth).tick();
            }
        }
    }
}
