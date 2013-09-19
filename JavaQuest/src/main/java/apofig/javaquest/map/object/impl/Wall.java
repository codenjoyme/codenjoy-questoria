package apofig.javaquest.map.object.impl;

import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Something;
import apofig.javaquest.map.object.TalkingObject;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 10:25 PM
 */
public class Wall extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        messenger.say("Ты не можешь это делать со мной!"); // TODO test it
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        messenger.say("Пожалуйста, остановись!");
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
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
    public void tryToLeave(Me hero) {
        // do nothing
    }
}
