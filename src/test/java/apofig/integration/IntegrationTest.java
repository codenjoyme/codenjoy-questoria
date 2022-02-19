package apofig.integration;

import apofig.integration.mocker.SpringMockerJettyRunner;
import apofig.javaquest.services.PlayerService;
import apofig.javaquest.services.TimerService;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

public class IntegrationTest {

    private static PlayerService players;
    private static TimerService timer;
    private static SpringMockerJettyRunner runner;
    private static String url;
    private static int port;

    @BeforeClass
    public static void setupJetty() throws Exception {
        runner = new SpringMockerJettyRunner("src/main/webapp", "/appcontext");
//        runner.spyBean("playerService");
        port = runner.start(new Random().nextInt(1000) + 10000);

        url = runner.getUrl();
        System.out.println(url);

//        players = runner.getBean(PlayerService.class, "playerService");
//        timer = runner.getBean(TimerService.class, "timerService");
//        timer.pause();
//
//        timer.resume();

//        runner.join();
    }

    @Test
    @Ignore
    public void test() {
        String player1Code = players.register("player1");

    }

}
