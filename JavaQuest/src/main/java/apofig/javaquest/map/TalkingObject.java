package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 12:35 AM
 */
public class TalkingObject extends MapObject implements ObjectSettings {

    protected Messages messages;

    public void say(String message) {
        messages.add(this.getClass().getSimpleName() + ": " + message);
    }

    public void clearLog() {
        messages.clear();
    }

    @Override
    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
