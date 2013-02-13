package apofig.javaquest.map.object;

import apofig.javaquest.map.Player;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:43 PM
 */
public class Nothing extends TalkingObject implements Something {

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
        return make(' ');
    }

    @Override
    public char symbol() {
        return ' ';
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }
}