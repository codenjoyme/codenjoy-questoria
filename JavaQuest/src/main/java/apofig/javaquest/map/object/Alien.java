package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Messages;
import apofig.javaquest.map.Player;

/**
 * User: sanja
 * Date: 10.07.13
 * Time: 0:59
 */
public class Alien extends TalkingObject implements Something {

    @Override
    public void answer(String message) {

    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        sayUnique("Привет, я такой же как и ты игрок!");
    }

    @Override
    public boolean iCanUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return make(' ');
    }

    @Override
    public char symbol() {
        return 'A';
    }

    @Override
    public void getBy(Player info) {

    }

    @Override
    public void tryToLeave() {
        say("Ну пока!");
    }

    @Override
    public void onKill(Action action) {

    }

    @Override
    public String getCode() {
        return "";
    }
}
