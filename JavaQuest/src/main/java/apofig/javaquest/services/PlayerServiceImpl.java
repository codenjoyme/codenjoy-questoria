package apofig.javaquest.services;

import apofig.javaquest.field.*;
import apofig.javaquest.field.object.monster.MonsterFactory;
import apofig.javaquest.field.object.monster.MonsterPoolImpl;
import apofig.javaquest.field.object.monster.MonsterLoader;
import apofig.javaquest.field.object.monster.MonsterPool;
import apofig.saver.Loader;
import apofig.saver.Saver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map;

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
            public FieldLoader fieldLoader() {
                return new LoadFieldFromFile("field.txt");
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
    public void tick() {
        game.tick();
    }

    @Override
    public Player getPlayerByCode(String playerGameCode) {
        return players.get(playerGameCode);
    }

    @Override
    public boolean alreadyRegistered(String playerName) {
        return getPlayerByName(playerName) != null;
    }

    private Player getPlayerByName(String playerName) {
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
        Collections.sort(result, Comparator.comparing(Player::getName));
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
        Player player = getPlayerByName(playerName);
        players.remove(player.getGameCode());
        game.remove(player.getName());
    }

}
