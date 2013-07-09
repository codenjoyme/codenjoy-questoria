package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.monster.MonsterPool;

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
            if (smth.isAt(place) && smth.symbol() == place.getChar()) {
                return smth;
            }
        }

        ObjectSettings object = getObject(place.getChar());
        object.setPlace(place);
        object.setFactory(this);

        Something smth = (Something)object;

        if (!(smth instanceof Nothing)) {
            objects.add(smth);
        }
        smth.onKill(new Action() {
            @Override
            public void act(Something object) {
                objects.remove(object);
            }
        });
        return smth;
    }

    private ObjectSettings getObject(char c) {
        if (c == ' ' || c == 'I') {
            return new Nothing();
        } else if (c == '@') {
            return monsters.next();
        } else if (c == '#') {
            return new Wall();
        } else if (c == '$') {
            return new Gold();
        }
        throw new UnsupportedOperationException("WTF! Новый объект в мире, а мы не в курсе: " + c);
    }
}
