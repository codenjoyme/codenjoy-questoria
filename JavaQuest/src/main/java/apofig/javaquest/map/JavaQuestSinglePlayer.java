package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.monster.CodeHelper;

/**
 * User: sanja
 * Date: 09.07.13
 * Time: 23:48
 */
public class JavaQuestSinglePlayer {

    private JavaQuest game;
    private Me player;

    public JavaQuestSinglePlayer(JavaQuest game) {
        this.game = game;
        player = game.newPlayer();
    }

    public String getMessage() {
        return player.getMessages().getLast(60);
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
}
