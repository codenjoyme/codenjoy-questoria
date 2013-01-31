package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
public class Monster extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        if (message.equals("die!")) {
            say("yOU @#& Ki$%@&^ll me $!@!");
            Something something = leaveAfter();
            place.update(something.symbol());
            something.askMe();
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
        return make('$');
    }

    @Override
    public char symbol() {
        return '@';
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }
}
