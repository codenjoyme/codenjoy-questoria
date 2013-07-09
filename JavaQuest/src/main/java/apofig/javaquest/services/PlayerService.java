package apofig.javaquest.services;

import apofig.javaquest.map.JavaQuest;
import apofig.javaquest.map.JavaQuestSinglePlayer;
import apofig.javaquest.map.Joystick;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:11
 */
public interface PlayerService {

    void nextStepForAllGames();

    JavaQuestSinglePlayer newGame();
}
