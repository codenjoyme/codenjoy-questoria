package com.codenjoy.dojo.questoria.model;

import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.questoria.model.items.monster.CodeHelper;

public class SinglePlayer {

    private Questoria game;
    private Me player;

    private SinglePlayer() {}

    public SinglePlayer(Questoria game, String name) {
        this.game = game;
        player = game.newPlayer(name);
    }

    public String getMessage() {
        return player.getMessenger().getMessages().getLast(60);
    }

    public PlayerInfoImpl getPlayerInfo() {
        return player.getInfo();
    }

    public Joystick getJoystick() {
        return player;
    }

    @Override
    public String toString() {
        return game.printView(player);
    }

    public CodeHelper getCodeHelper() {
        return game.getCodeHelper(player);
    }

    public void tick() {
        game.tick();
    }
}
