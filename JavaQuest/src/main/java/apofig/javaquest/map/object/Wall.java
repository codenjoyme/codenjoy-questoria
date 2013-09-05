package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 10:25 PM
 */
public class Wall extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        say("Ты не можешь это делать со мной!"); // TODO test it
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        say("Пожалуйста, остановись!");
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

    @Override
    public void tryToLeave() {
        // do nothing
    }

    @Override
    public String getCode() {
        return "";
    }
}
