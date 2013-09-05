package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public class TalkingObject implements ObjectSettings, SetWorld {

    private List<Messages> messages;
    protected World world;

    public TalkingObject() {
        messages = new LinkedList<>();
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    public void say(String message) {
        for (Messages m : messages) {
            m.add(getName() + ": " + message);
        }
    }

    public void sayOnce(String message) {
        for (Messages m : messages) {
            m.addOnce(getName() + ": " + message);
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
        Something something = world.make(c);
        ((TalkingObject)something).messages = messages; // TODO ну очень некрасиво
        return something;
    }

    public void move(int x, int y) {
        world.move(x, y);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
