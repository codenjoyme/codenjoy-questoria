package apofig.javaquest.services;

import apofig.javaquest.map.JavaQuest;
import apofig.javaquest.map.JavaQuestSinglePlayer;
import apofig.javaquest.map.Joystick;

import java.util.Collection;
import java.util.List;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:11
 */
public interface PlayerService {

    void nextStepForAllGames();

    Player loadGame(String playerGameCode);

    boolean alreadyRegistered(String playerName);

    String register(String name);

    List<Player> players();

    void saveAllGame();

    void loadAllGame();

    void remove(String playerName);
}
