package apofig.javaquest.services;

import apofig.javaquest.field.JavaQuest;
import apofig.javaquest.field.Joystick;
import apofig.javaquest.field.Player;
import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.monster.CodeHelper;

public class JavaQuestSinglePlayer {

    private JavaQuest game;
    private Me player;

    private JavaQuestSinglePlayer() {}

    public JavaQuestSinglePlayer(JavaQuest game, String name) {
        this.game = game;
        player = game.newPlayer(name);
    }

    public String getMessage() {
        return player.getMessenger().getMessages().getLast(60);
    }

    public Player getPlayerInfo() {
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
