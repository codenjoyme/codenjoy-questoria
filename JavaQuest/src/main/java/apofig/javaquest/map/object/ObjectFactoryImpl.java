package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.monster.MonsterPool;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:52 PM
 */
public class ObjectFactoryImpl implements ObjectFactory {
    private Messages messages;
    private MonsterPool monsters;

    public ObjectFactoryImpl(Messages messages, MonsterPool monsters) {
        this.messages = messages;
        this.monsters = monsters;
    }

    @Override
    public Something make(char c, Place place) {
        ObjectSettings object = getObject(c);
        object.setPlace(place);
        object.setFactory(this);
        object.setMessages(messages);
        return (Something)object;
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
