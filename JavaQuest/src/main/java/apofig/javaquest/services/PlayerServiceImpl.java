package apofig.javaquest.services;

import apofig.javaquest.map.JavaQuest;
import apofig.javaquest.map.LoadMapFromFile;
import apofig.javaquest.map.MapLoader;
import apofig.javaquest.map.Settings;
import apofig.javaquest.map.object.monster.MonsterFactoryImpl;
import apofig.javaquest.map.object.monster.MonsterPool;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:10
 */
@Component("playerService")
public class PlayerServiceImpl implements PlayerService {

    private List<JavaQuest> games;

    public PlayerServiceImpl() {
        games = new LinkedList<JavaQuest>();
    }

    private Settings getSettings() {
        return new Settings() {
            @Override
            public int getViewAreaSize() {
                return 41;
            }

            @Override
            public MapLoader getMapLoader() {
                return new LoadMapFromFile("map.txt");
            }

            @Override
            public MonsterPool getMonsters() {
                return new MonsterFactoryImpl();
            }
        };
    }

    @Override
    public void nextStepForAllGames() {
        for (JavaQuest game : games) {
            game.tick();
        }
    }

    @Override
    public JavaQuest newGame() {
        JavaQuest game = new JavaQuest(getSettings());
        games.add(game);
        return game;
    }

}
