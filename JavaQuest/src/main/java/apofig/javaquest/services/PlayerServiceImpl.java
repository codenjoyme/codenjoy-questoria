package apofig.javaquest.services;

import apofig.javaquest.map.*;
import apofig.javaquest.map.object.monster.MonsterFactoryImpl;
import apofig.javaquest.map.object.monster.MonsterPool;
import org.springframework.stereotype.Component;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:10
 */
@Component("playerService")
public class PlayerServiceImpl implements PlayerService {

    private JavaQuest game;

    public PlayerServiceImpl() {
        game = new JavaQuest(settings());
    }

    private Settings settings() {
        return new Settings() {
            @Override
            public int viewSize() {
                return 41;
            }

            @Override
            public MapLoader mapLoader() {
                return new LoadMapFromFile("map.txt");
            }

            @Override
            public MonsterPool monsters() {
                return new MonsterFactoryImpl();
            }
        };
    }

    @Override
    public void nextStepForAllGames() {
        game.tick();
    }

    @Override
    public JavaQuestSinglePlayer newGame(String playerName) {
        return new JavaQuestSinglePlayer(game, playerName);
    }

}
