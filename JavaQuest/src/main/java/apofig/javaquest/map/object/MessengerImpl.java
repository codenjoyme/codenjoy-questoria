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

    private LinkedList<Messages> messages;
    private String name;

    public MessengerImpl() {
        this.messages = new LinkedList<Messages>();
    }

    @Override
    public void say(String message) {
        for (Messages m : messages) {
            m.add(buildMessage(message));
        }
    }

    private String buildMessage(String message) {
        return name + ": " + message;
    }

    @Override
    public void sayOnce(String message) {
        for (Messages m : messages) {
            m.addOnce(buildMessage(message));
        }
    }

    @Override
    public Messages getMessages() {
        return messages.getFirst();
    }

    @Override
    public void sayToLast(String message) {
        messages.getLast().add(buildMessage(message));
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