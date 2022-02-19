package apofig.javaquest.services;

import apofig.javaquest.field.FieldLoader;
import apofig.javaquest.field.JavaQuest;
import apofig.javaquest.field.LoadFieldFromFile;
import apofig.javaquest.field.Settings;
import apofig.javaquest.field.object.monster.MonsterFactory;
import apofig.javaquest.field.object.monster.MonsterLoader;
import apofig.javaquest.field.object.monster.MonsterPool;
import apofig.javaquest.field.object.monster.MonsterPoolImpl;
import apofig.saver.Loader;
import apofig.saver.Saver;
import com.codenjoy.dojo.services.Tickable;

import java.util.*;

public class Runner implements Tickable {

    private JavaQuest game;
    private java.util.Map<String, Player> players;

    public Runner() {
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

    public Player getPlayerByCode(String playerGameCode) {
        return players.get(playerGameCode);
    }

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

    public String register(String playerName) {
        Player player = new Player(playerName, game);
        players.put(player.getGameCode(), player);
        return player.getGameCode();
    }

    public List<Player> players() {
        List<Player> result = new LinkedList<>(players.values());
        Collections.sort(result, Comparator.comparing(Player::getName));
        return result;
    }

    public void saveAllGame() {
        String json = new Saver().save(this);
        new WithFile("save_game.json").save(json);
    }

    public void loadAllGame() {
        String json = new WithFile("save_game.json").load();
        Runner service = (Runner)new Loader().load(json);
        this.game = service.game;
        this.players = service.players;
    }

    public void remove(String playerName) {
        Player player = getPlayerByName(playerName);
        players.remove(player.getGameCode());
        game.remove(player.getName());
    }
}