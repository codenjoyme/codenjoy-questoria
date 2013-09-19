package apofig.javaquest.map.object.impl;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.*;
import apofig.javaquest.map.object.monster.CodeHelper;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:43 PM
 */
public class Nothing extends TalkingObject implements Something, CodeHelper, SetWorld {

    private World world;

    @Override
    public void answer(String message) {
        messenger.say("Ну и?..");
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
//        clearLog();  // TODO реализовать очистку лога после двух подряд Nothing
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public Something leaveAfter() {
        return world.make(' ');
    }

    @Override
    public char symbol() {
        return ' ';
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }

    @Override
    public void tryToLeave(Me hero) {
        // do nothing
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
