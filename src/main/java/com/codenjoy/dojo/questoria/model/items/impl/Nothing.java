package com.codenjoy.dojo.questoria.model.items.impl;

import com.codenjoy.dojo.questoria.model.PlayerInfoImpl;
import com.codenjoy.dojo.questoria.model.items.Something;
import com.codenjoy.dojo.questoria.model.items.TalkingObject;
import com.codenjoy.dojo.questoria.model.items.Usable;
import com.codenjoy.dojo.questoria.model.items.monster.CodeHelper;

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
    public void getBy(PlayerInfoImpl info) {
        // do nothing
    }
}
