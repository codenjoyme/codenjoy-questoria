package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public class MessengerImpl implements Messenger {

    private List<Messages> messages;
    private String name;

    public MessengerImpl() {
        this.messages = new LinkedList<>();
    }

    @Override
    public void say(String message) {
        for (Messages m : messages) {
            m.add(name + ": " + message);
        }
    }

    @Override
    public void sayOnce(String message) {
        for (Messages m : messages) {
            m.addOnce(name + ": " + message);
        }
    }

    @Override
    public Messages getMessages() {
        return messages.get(0);
    }

    @Override
    public void add(Messages messages) {
        if (this.messages.contains(messages)) {
            return;
        }

        this.messages.add(messages);
    }

    @Override
    public void remove(Messages messages) {
        this.messages.remove(messages);
    }

    @Override
    public void changeName(String name) {
        this.name = name;
    }

}