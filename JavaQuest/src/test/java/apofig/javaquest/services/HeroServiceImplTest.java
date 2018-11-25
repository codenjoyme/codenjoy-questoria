package apofig.javaquest.services;

import apofig.client.Solver;
import apofig.client.WebSocketRunner;
import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.PortalMessenger;
import apofig.javaquest.field.object.monster.CodeRunnerMonster;
import apofig.javaquest.field.object.monster.OneIntCodeRunnerMonster;
import apofig.javaquest.transport.PlayerSocket;
import apofig.javaquest.transport.Socket;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HeroServiceImplTest {

    private HeroService heroes;
    private PlayerService players;

    @Before
    public void setUp() {
        players = new PlayerServiceImpl();
        heroes = new HeroServiceImpl() {{
            players = HeroServiceImplTest.this.players;
        }};
    }

    @Test
    public void testGetHeroes() {
        // given
        players.register("player1");
        players.register("player2");
        players.register("player3");

        // when
        List<Me> mes = heroes.getHeroes();

        // then
        assertEquals("[Player: 'Уровень:0 Опыт:0 Здоровье:100 Золото:0' in place: '[object Me at field[93,482]='A']', " +
                "Player: 'Уровень:0 Опыт:0 Здоровье:100 Золото:0' in place: '[object Me at field[94,482]='A']', " +
                "Player: 'Уровень:0 Опыт:0 Здоровье:100 Золото:0' in place: '[object Me at field[95,482]='A']']", mes.toString());
    }

    @Test
    public void testGetMonsterPortal_someFailures() throws IOException {
// given
        OneIntCodeRunnerMonster.COUNT_TESTS = 10;
        Me hero = givenHeroNearMonster();

        // then
        assertEquals("8B4AF25C", hero.getPortal().getHash());

        // when
        PortalMessenger messenger = heroes.getPortalMessenger("8B4AF25C");

        // then
        // simulate connect WS client+server
        WebSocketRunner.ClientSocket client = new WebSocketRunner.ClientSocket(getFizzBuzzAlgorythm("BUG"));

        PlayerSocket server = new PlayerSocket(messenger);
        messenger.portalCreated(server);

        Session serverSession = mock(Session.class);
        Session clientSession = mock(Session.class);

        RemoteEndpoint serverRemote = emulateConnection(client, serverSession, clientSession);
        RemoteEndpoint clientRemote = emulateConnection(server, clientSession, serverSession);

        // communication
        players.tick(); // question
        for (int i = 0; i < OneIntCodeRunnerMonster.COUNT_TESTS; i++) {
            players.tick(); // answer, question
        }

        // check questions
        ArgumentCaptor<String> questionCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverRemote, atLeastOnce()).sendString(questionCaptor.capture());
        assertEquals("[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]]", questionCaptor.getAllValues().toString());

        // check test status
        assertEquals("Для [1] метод работает правильно - ты вернул “1”\n" +
                "Для [2] метод работает правильно - ты вернул “2”\n" +
                "Для [3] метод должен вернуть “Fizz”, но ты вернул “FizzBUG”\n" +
                "Для [4] метод работает правильно - ты вернул “4”\n" +
                "Для [5] метод должен вернуть “Buzz”, но ты вернул “BuzzBUG”\n" +
                "Для [6] метод должен вернуть “Fizz”, но ты вернул “FizzBUG”\n" +
                "Для [7] метод работает правильно - ты вернул “7”\n" +
                "Для [8] метод работает правильно - ты вернул “8”\n" +
                "Для [9] метод должен вернуть “Fizz”, но ты вернул “FizzBUG”\n" +
                "Для [10] метод должен вернуть “Buzz”, но ты вернул “BuzzBUG”", ((CodeRunnerMonster)messenger).getTestSuite().getResults());
    }

    private Me givenHeroNearMonster() {
        players.register("player1");
        Me hero = heroes.getHeroes().get(0);

        hero.moveLeft();
        players.tick();

        hero.moveLeft();
        players.tick();

        hero.moveLeft();
        players.tick();

        hero.moveLeft();
        players.tick();
        return hero;
    }

    @Test
    public void testGetMonsterPortal_allTestsAreSuccess() throws IOException {
        // given
        OneIntCodeRunnerMonster.COUNT_TESTS = 10;
        Me hero = givenHeroNearMonster();

        // then
        assertEquals("8B4AF25C", hero.getPortal().getHash());

        // when
        PortalMessenger messenger = heroes.getPortalMessenger("8B4AF25C");

        // then
        // simulate connect WS client+server
        WebSocketRunner.ClientSocket client = new WebSocketRunner.ClientSocket(getFizzBuzzAlgorythm(""));

        PlayerSocket server = new PlayerSocket(messenger);
        messenger.portalCreated(server);

        Session serverSession = mock(Session.class);
        Session clientSession = mock(Session.class);

        RemoteEndpoint serverRemote = emulateConnection(client, serverSession, clientSession);
        RemoteEndpoint clientRemote = emulateConnection(server, clientSession, serverSession);

        // communication
        players.tick(); // question
        for (int i = 0; i < OneIntCodeRunnerMonster.COUNT_TESTS; i++) {
            players.tick(); // answer, question
        }

        // check questions
        ArgumentCaptor<String> questionCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverRemote, atLeastOnce()).sendString(questionCaptor.capture());
        assertEquals("[[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]]", questionCaptor.getAllValues().toString());

        // check test status
        assertEquals("OK", ((CodeRunnerMonster)messenger).getTestSuite().getResults());

        // monster die && portal closed
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0 Портал: 8B4AF25C", hero.getInfo().toString());
        players.tick();
        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:0", hero.getInfo().toString());

        // get gold
        hero.moveLeft();
        players.tick();

        assertEquals("Уровень:0 Опыт:0 Здоровье:100 Золото:10", hero.getInfo().toString());
    }

    private Solver getFizzBuzzAlgorythm(String bug) {
        return new Solver() {
            @Override
            public String getAnswer(String... parameters) {
                int i = Integer.valueOf(parameters[0]);

                String result = "";
                if (i % 3 == 0) {
                    result += "Fizz" + bug;
                }
                if (i % 5 == 0) {
                    result += "Buzz" + bug;
                }
                if (result.length() == 0) {
                    result = String.valueOf(i);
                }
                return result;
            }
        };
    }

    private RemoteEndpoint emulateConnection(Socket socket, Session server, Session client) throws IOException {
        when(server.isOpen()).thenReturn(true);
        RemoteEndpoint serverRemote = mock(RemoteEndpoint.class);
        when(server.getRemote()).thenReturn(serverRemote);
        doAnswer(inv -> {
            String question = inv.getArgument(0);
            socket.onText(client, question);
            return null;
        }).when(serverRemote).sendString(anyString());
        socket.onConnect(client);
        return serverRemote;
    }

}