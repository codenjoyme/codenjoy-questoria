package apofig.javaquest.map;

import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 10:25 PM
 */
public class Wall extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        say("You can't do this!"); // TODO test it
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        say("Please stop!");
    }

    @Override
    public boolean iCanUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return make(symbol());
    }

    @Override
    public char symbol() {
        return '#';
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }
}
