package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public class TalkingObject extends MapObject implements ObjectSettings {

    private List<Messages> messages;

    public TalkingObject() {
        messages = new LinkedList<>();
    }

    public void say(String message) {
        for (Messages m : messages) {
            m.add(getName() + ": " + message);
        }
    }

    public void sayOnce(String message) {  // TODO подумать об этом
        for (Messages m : messages) {
            m.addUnique(getName() + ": " + message);
        }
    }

    @Override
    public void add(Messages messages) {
        if (this.messages.contains(messages)) {
            return;
        }

        this.messages.add(messages);
    }

    public Messages getMessages() {
        return messages.get(0);
    }

    public void meetWith(Me me) {
        if (messages.contains(me.getMessages())) {
            return;
        }

        messages.add(me.getMessages());
    }

    public void leave(Me me) {
        messages.remove(me.getMessages());
    }

    public Something make(char c) {
        Something something = super.make(c);
        ((TalkingObject)something).messages = messages; // TODO ну очень некрасиво
        return something;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
