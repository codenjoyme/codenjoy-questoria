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
public class Nothing extends TalkingObject implements Something, CodeHelper, Usable {

    @Override
    public void answer(String message) {
        messenger.say("Ну и?..");
    }

    @Override
    public void ask() {
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return ' ';
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }
}
