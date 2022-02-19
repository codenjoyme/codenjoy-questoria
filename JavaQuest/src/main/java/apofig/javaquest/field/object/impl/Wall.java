package apofig.javaquest.field.object.impl;

import apofig.javaquest.field.object.Something;
import apofig.javaquest.field.object.TalkingObject;

public class Wall extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        messenger.sayOnce("Ты не можешь это делать со мной!");
    }

    @Override
    public void ask() {
        messenger.say("Пожалуйста, остановись!");
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return '#';
    }
}
