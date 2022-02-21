package com.codenjoy.dojo.questoria.model;

public class Player {
    private SinglePlayer game;
    private String name;
    private String gameCode;

    public Player(String name, Questoria game) {
        this.name = name;
        this.game = new SinglePlayer(game, name);
        this.gameCode = name + name.hashCode();
    }

    public Player() {
        // do nothing
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public SinglePlayer getGame() {
        return game;
    }
}
