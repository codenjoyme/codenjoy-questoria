package com.codenjoy.dojo.questoria.model.items.impl;

import com.codenjoy.dojo.questoria.model.items.Something;
import com.codenjoy.dojo.questoria.model.items.TalkingObject;

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
