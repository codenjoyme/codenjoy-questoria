package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
public class Monster extends TalkingObject implements Something {

    private final OnKill onKill;

    public Monster(Messages messages, OnKill onKill) {
        super(messages);
        this.onKill = onKill;
    }

    @Override
    public void answer(String message) {
        if (message.equals("die!")) {
            say("yOU @#& Ki$%@&^ll me $!@!");
            leaveAfter().askMe();
            onKill.doit(this);
        } else {
            say("I'll kill you!");
        }
    }

    @Override
    public boolean iCanLeave() {
        return false;
    }

    @Override
    public void askMe() {
        say("Fight with me!");
    }

    @Override
    public boolean iCanUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return new Gold(messages);
    }

    @Override
    public char symbol() {
        return '@';
    }
}
