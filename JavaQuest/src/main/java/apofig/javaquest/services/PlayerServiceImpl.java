package apofig.javaquest.services;

import apofig.javaquest.map.JavaQuest;
import apofig.javaquest.map.LoadMapFromFile;
import apofig.javaquest.map.MapLoader;
import apofig.javaquest.map.Settings;
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

    private final JavaQuest game;

    public PlayerServiceImpl() {
        Settings settings = new Settings() {
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

        game = new JavaQuest(settings);
    }

    @Override
    public void nextStepForAllGames() {
        game.tick();
    }

    @Override
    public JavaQuest getGame() {
        return game;
    }

}
