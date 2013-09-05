package apofig.javaquest.map.object;

import apofig.javaquest.map.Dieble;
import apofig.javaquest.map.Point;
import apofig.javaquest.map.object.dron.Dron;
import apofig.javaquest.map.object.dron.DronMentor;
import apofig.javaquest.map.object.monster.MonsterPool;
import apofig.javaquest.services.Tickable;
import org.fest.reflect.core.Reflection;

import java.util.*;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:52 PM
 */
public class ObjectFactoryImpl implements ObjectFactory {

    private MonsterPool monsters;
    private Map<Something, World> objects;

    public ObjectFactoryImpl(MonsterPool monsters) {
        this.monsters = monsters;
        objects = new HashMap<Something, World>();
    }

    @Override
    public Something get(Place place) {
        Messenger messenger = null;

        for (Something smth : getObjects()) {
            if (isAt(smth, place)) {
                if (smth.symbol() != place.getChar()) {
                    killSomething(smth);
                    messenger = ((TalkingObject)smth).getMessenger();
                } else {
                    return smth;
                }
            }
        }

        return initObject(place, messenger);
    }

    private Collection<Something> getObjects() {
        return new LinkedList<>(objects.keySet());
    }

    @Override
    public boolean isAt(Something smth, Point place) {
        if (smth instanceof Me) {
            return ((Me)smth).isAt(place);
        }
        return objects.get(smth).place().isAt(place);
    }

    private Something initObject(Place place, Messenger messenger) {
        Something result = newObject(place.getChar());

        WorldImpl world = new WorldImpl(this, place, result);

        if (result instanceof Nothing) {
            return result;
        }

        objects.put(result, world);

        if (messenger == null) {
            messenger = new MessengerImpl();
        }
        ((SetMessenger)result).setMessenger(messenger);

        if (SetPlace.class.isAssignableFrom(result.getClass())) {
            ((SetPlace)result).setPlace(place);
        }

        if (SetWorld.class.isAssignableFrom(result.getClass())) {
            ((SetWorld)result).setWorld(world);
        }

        return result;
    }

    private void killSomething(Something smth) {
        objects.remove(smth);
        if (Dieble.class.isAssignableFrom(smth.getClass())) {
            ((Dieble)smth).die();
        }
    }

    @Override
    public void add(Me me) {
        objects.put(me, me.getWorld());
    }

    private Something newObject(char c) {
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
        for (Something smth : getObjects()) {
            result.add(Reflection.field("world").ofType(World.class).in(smth).get().toString());
        }
        return result.toString();
    }

    /**
     * Любой объект мира (Something) маркированный как Tickable будет каждый тик оповещаться от JavaQuest
     */
    @Override
    public void tick() {
        for (Something smth : getObjects()) {
            if (Tickable.class.isAssignableFrom(smth.getClass())) {
                ((Tickable)smth).tick();
            }
        }
    }
}
