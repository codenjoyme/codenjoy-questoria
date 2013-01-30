package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:43 PM
 */
public class Nothing extends TalkingObject implements Something {

    public Nothing(Messages messages) {
        super(messages);
    }

    @Override
    public void answer(String message) {
        clearLog();
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        clearLog();
    }

    @Override
    public boolean iCanUse() {
        return true;
    }

    @Override
    public Something leaveAfter() {
        return new Nothing(messages);
    }

    @Override
    public char symbol() {
        return ' ';
    }
}
