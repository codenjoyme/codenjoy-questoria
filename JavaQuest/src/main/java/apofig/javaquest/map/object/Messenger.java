package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

/**
 * User: sanja
 * Date: 06.09.13
 * Time: 1:22
 */
public interface Messenger {
    void sayOnce(String message);

    void say(String message);

    Messages getMessages();

    void add(Messages messages);

    void remove(Messages messages);

    void changeName(String name);

    void sayToLast(String message);
}
