package apofig.javaquest.field.object;

import apofig.javaquest.field.Dieble;
import apofig.javaquest.field.Locator;
import apofig.javaquest.field.Point;
import apofig.javaquest.field.TerritoryField;
import apofig.javaquest.field.object.impl.Nothing;
import apofig.javaquest.field.object.monster.MonsterFactory;
import apofig.javaquest.field.object.monster.MonsterPool;
import apofig.javaquest.services.Tickable;

import java.util.*;

public class ObjectFactoryImpl implements ObjectFactory {
    // TODO мне кажется эта штука должна быть для каждого юзера отдельной, покуда монстры и комни предткновения у каждого юзера свои...

    private Locator locator;
    private ObjectLoader loader;
    private Map<String, MonsterPool> monsters;
    private Map<Something, World> objects;
    private MonsterFactory monstersFactory;

    private ObjectFactoryImpl() {}

    public ObjectFactoryImpl(MonsterFactory factory, TerritoryField field) {
        monstersFactory = factory;
        locator = new Locator(field, this);
        monsters = new HashMap<String, MonsterPool>();
        objects = new HashMap<Something, World>();
        loader = new ObjectLoader();
    }

    @Override
    public Locator getLocator() {
        return locator;
    }

    @Override
    public Something get(Place place, Me founder, Object params) {
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

        return initObject(place, messenger, founder, params);
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

    private Something initObject(Place place, Messenger messenger, Me founder, Object params) {
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

        if (SetParameters.class.isAssignableFrom(result.getClass())) {
            ((SetParameters)result).setParameters(params);
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
