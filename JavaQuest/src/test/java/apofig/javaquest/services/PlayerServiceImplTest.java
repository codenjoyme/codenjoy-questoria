package apofig.javaquest.services;

import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class PlayerServiceImplTest {

    @Test
    public void test() {
        PlayerService players = new PlayerServiceImpl();

        assertEquals("player1-493567632", players.register("player1"));
        assertEquals("player2-493567631", players.register("player2"));

        assertEquals(true, players.alreadyRegistered("player1"));
        assertEquals(true, players.alreadyRegistered("player2"));
        assertEquals(false, players.alreadyRegistered("player3"));

        assertEquals("[player1, player2]",
                players.players().stream()
                        .map(Player::getName)
                        .collect(toList())
                        .toString());

        players.remove("player2");

        assertEquals(false, players.alreadyRegistered("player2"));

        assertEquals("player1", players.getPlayerByCode("player1-493567632").getName());
    }

}