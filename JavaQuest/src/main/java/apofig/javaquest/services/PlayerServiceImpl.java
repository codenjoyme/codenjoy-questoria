package apofig.javaquest.services;

import apofig.javaquest.map.*;
import apofig.javaquest.map.object.monster.MonsterFactory;
import apofig.javaquest.map.object.monster.MonsterPoolImpl;
import apofig.javaquest.map.object.monster.MonsterLoader;
import apofig.javaquest.map.object.monster.MonsterPool;
import apofig.saver.Loader;
import apofig.saver.Saver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:10
 */
@Component("playerService")
public class PlayerServiceImpl implements PlayerService {   // TODO test me

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
            public MonsterFactory monsters() {
                return new MonsterFactoryImpl();
            }
        };
    }

    static class MonsterFactoryImpl implements MonsterFactory {

        @Override
        public MonsterPool newMonsters() {
            return new MonsterPoolImpl(new MonsterLoader());
        }
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
        return getPlayer(playerName) != null;
    }

    private Player getPlayer(String playerName) {
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            if (entry.getValue().getName().equals(playerName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String register(String playerName) {   
        Player player = new Player(playerName, game);
        players.put(player.getGameCode(), player);
        return player.getGameCode();
    }

    @Override
    public List<Player> players() {
        List<Player> result = new LinkedList<>(players.values());
        Collections.sort(result, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return result;
    }

    @Override
    public void saveAllGame() {     
        String json = new Saver().save(this);
        new WithFile("save_game.json").save(json);
    }

    @Override
    public void loadAllGame() {    
        String json = new WithFile("save_game.json").load();
        PlayerServiceImpl service = (PlayerServiceImpl)new Loader().load(json);
        this.game = service.game;
        this.players = service.players;
    }

    @Override
    public void remove(String playerName) {
        Player player = getPlayer(playerName);
        players.remove(player.getGameCode());
        game.remove(player.getName());
    }

}
