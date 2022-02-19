package apofig.javaquest.services;

import apofig.javaquest.field.JavaQuest;
import apofig.javaquest.field.JavaQuestSinglePlayer;

public class Player {
    private JavaQuestSinglePlayer game;
    private String name;
    private String gameCode;

    public Player(String name, JavaQuest game) {
        this.name = name;
        this.game = new JavaQuestSinglePlayer(game, name);
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

    public JavaQuestSinglePlayer getGame() {
        return game;
    }
}
