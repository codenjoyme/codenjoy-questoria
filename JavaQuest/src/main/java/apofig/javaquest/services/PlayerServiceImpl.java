package apofig.javaquest.services;

import apofig.javaquest.map.*;
import apofig.javaquest.map.object.monster.MonsterFactoryImpl;
import apofig.javaquest.map.object.monster.MonsterLoader;
import apofig.javaquest.map.object.monster.MonsterPool;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:10
 */
@Component("playerService")
public class PlayerServiceImpl implements PlayerService {

    private JavaQuest game;
    private java.util.Map<String, Player> players;

    public PlayerServiceImpl() {
        game = new JavaQuest(settings());
        players = new HashMap<>();
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
                return new MonsterFactoryImpl(new MonsterLoader());
            }
        };
    }

    @Override
    public void nextStepForAllGames() {
        game.tick();
    }

    @Override
    public Player loadGame(String playerGameCode) {
        return players.get(playerGameCode);
    }

    @Override
    public boolean alreadyRegistered(String playerName) {
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            if (entry.getValue().getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String register(String playerName) {
        Player player = new Player(playerName, game);
        players.put(player.getGameCode(), player);
        return player.getGameCode();
    }

}
