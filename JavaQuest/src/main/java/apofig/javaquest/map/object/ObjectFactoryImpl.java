package apofig.javaquest.map.object;

import apofig.javaquest.map.Dieble;
import apofig.javaquest.map.Locator;
import apofig.javaquest.map.Point;
import apofig.javaquest.map.TerritoryMap;
import apofig.javaquest.map.object.impl.Nothing;
import apofig.javaquest.map.object.monster.MonsterFactory;
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
    // TODO мне кажется эта штука должна быть для каждого юзера отдельной, покуда монстры и комни предткновения у каждого юзера свои...

    private Locator locator;
    private ObjectLoader loader;
    private Map<String, MonsterPool> monsters;
    private Map<Something, World> objects;
    private MonsterFactory monstersFactory;

    private ObjectFactoryImpl() {}

    public ObjectFactoryImpl(MonsterFactory factory, TerritoryMap map) {
        monstersFactory = factory;
        locator = new Locator(map, this);
        monsters = new HashMap<String, MonsterPool>();
        objects = new HashMap<Something, World>();
        loader = new ObjectLoader();
    }

    @Override
    public Locator getLocator() {
        return locator;
    }

    @Override
    public Something get(Place place, Me founder) {
        if (founder instanceof Me.DummyMe) {
            founder = ((Me.DummyMe)founder).getRealMe();
        }

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

        return initObject(place, messenger, founder);
    }

    public Collection<Something> getObjects() {
        return new LinkedList<Something>(objects.keySet());
    }

    private boolean isAt(Something smth, Point place) {
        if (smth instanceof Me) {
            return ((Me)smth).isAt(place);
        }
        return objects.get(smth).place().isAt(place);
    }

    @Override
    public void remove(Me me) {
        objects.remove(me);
        monsters.remove(me.getName());
    }

    private Something initObject(Place place, Messenger messenger, Me founder) {
        Something result = newObject(place.getChar(), founder);

        WorldImpl world = new WorldImpl(this, place, result, founder);

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

        if (SetLocator.class.isAssignableFrom(result.getClass())) {
            ((SetLocator)result).setLocator(locator);
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
        monsters.put(me.getName(), monstersFactory.newMonsters());
    }

    private Something newObject(char c, Me founder) {
        if (c == ' ' || c == 'I') {
            return new Nothing();
        } else if (c == 'A') {
            throw new IllegalStateException("Незареганный Alien!!!");
        } else if (c == '@') {
            return monsters.get(founder.getName()).next();
        }

        return loader.load(c);
    }

    public static UnsupportedOperationException newObjectError(String c) {
        throw new UnsupportedOperationException("WTF! Новый объект в мире, а мы не в курсе: '" + c + "'");
    }

    @Override
    public void tick() {
        for (Something smth : getObjects()) {
            if (Tickable.class.isAssignableFrom(smth.getClass())) {
                ((Tickable)smth).tick();
            }
        }
    }

}
