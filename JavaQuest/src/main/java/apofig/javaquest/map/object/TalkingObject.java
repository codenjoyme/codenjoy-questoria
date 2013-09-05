package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public class TalkingObject {

    private Messenger messenger;

    public void meetWith(Me hero) {
        this.add(hero.getMessages());
        if (this instanceof Me) {
            hero.add(this.getMessages());
        }
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void say(String message) {
        messenger.say(message);
    }

    public void sayOnce(String message) {
        messenger.sayOnce(message);
    }

    public Messages getMessages() {
        return messenger.getMessages();
    }

    public void add(Messages messages) {
        messenger.add(messages);
    }

    public void leave(Me me) {
        messenger.remove(me.getMessages());
    }

    public void init(Messenger messenger) {
        this.messenger = messenger;
        this.messenger.changeName(getName());
    }

    public String getName() {
        return getClass().getSimpleName();
    }
}
