package apofig.javaquest.map.object;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public abstract class TalkingObject implements SetMessenger, Askable {

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

    public void connect(TalkingObject object) {
        getMessenger().add(object.messenger.getMessages());
    }

    public void disconnect(TalkingObject object) {
        getMessenger().remove(object.messenger.getMessages());
    }
}
