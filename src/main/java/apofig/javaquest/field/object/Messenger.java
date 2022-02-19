package apofig.javaquest.field.object;

import apofig.javaquest.field.Messages;

public interface Messenger {
    void sayOnce(String message);

    void say(String message);

    Messages getMessages();

    void add(Messages messages);

    void remove(Messages messages);

    void changeName(String name);

    void sayToLast(String message);
}
