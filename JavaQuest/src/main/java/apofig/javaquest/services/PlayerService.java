package apofig.javaquest.services;

import apofig.javaquest.map.JavaQuest;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:11
 */
public interface PlayerService {

    void nextStepForAllGames();

    JavaQuest getGame();
}
