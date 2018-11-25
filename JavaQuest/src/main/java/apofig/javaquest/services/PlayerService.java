package apofig.javaquest.services;

import java.util.List;

public interface PlayerService extends Tickable {

    Player getPlayerByCode(String playerGameCode);

    boolean alreadyRegistered(String playerName);

    String register(String name);

    List<Player> players();

    void remove(String playerName);

    // TODO кажется эти два метода не должны быть тут
    void saveAllGame();
    void loadAllGame();
}
