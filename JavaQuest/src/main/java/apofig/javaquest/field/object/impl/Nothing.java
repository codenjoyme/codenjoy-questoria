package apofig.javaquest.field.object.impl;

import apofig.javaquest.field.Player;
import apofig.javaquest.field.object.*;
import apofig.javaquest.field.object.monster.CodeHelper;

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
