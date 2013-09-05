package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public class TalkingObject implements SetMessenger {

    protected Messenger messenger;

    public Messenger getMessenger() {
        return messenger;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
        this.messenger.changeName(getName());
    }
}
