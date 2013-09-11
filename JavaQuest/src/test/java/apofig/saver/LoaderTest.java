package apofig.saver;

import apofig.javaquest.map.object.monster.Monster;
import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import apofig.javaquest.services.PlayerServiceImpl;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: sanja
 * Date: 09.09.13
 * Time: 21:35
 */
public class LoaderTest {

    @Test
    public void test() {
        Object object = getObjectTree();

        String expected = toString(object);

        Object newObject = load(expected);

        String actual = toString(newObject);
        assertEquals(ln(expected), ln(actual));

    }

    private PlayerService getObjectTree() {
        PlayerService result = new PlayerServiceImpl();
        Player player1 = result.loadGame(result.register("player1"));
        Player player2 = result.loadGame(result.register("player2"));

        player1.getGame().getJoystick().moveDown();
        player2.getGame().getJoystick().moveLeft();

        result.nextStepForAllGames();
        return result;
    }

    private Object load(String expected) {
        return new Loader().load(expected);
    }

    private String toString(Object object) {
        return new Saver()./*exclude(char[][].class, char[].class).excludeChildren(Monster.class).*/save(object);
    }

    private String ln(String test) {
        return test.replaceAll("\\{\"id\"", "\n{\"id\"");
    }

    @Test
    public void arrayOfArrayOfChar() {
        ArrayOfArrayOfCharContainer object = new ArrayOfArrayOfCharContainer(6);
        String expected = new Saver().save(object);
        assertEquals("{\"objects\":[{\"id\":\"ArrayOfArrayOfCharContainer@0\",\"type\":\"apofig.saver.ArrayOfArrayOfCharContainer\",\"fields\":[{\"array\":\"char[][]@1\"}]},{\"id\":\"char[][]@1\",\"type\":\"[[C\",\"fields\":[\"char[]@2\",\"char[]@3\"]},{\"id\":\"char[]@2\",\"type\":\"[C\",\"fields\":[\"abc\"]},{\"id\":\"char[]@3\",\"type\":\"[C\",\"fields\":[\"qwe\"]}],\"main\":\"ArrayOfArrayOfCharContainer@0\"}", expected);

        Object load = new Loader().load(expected);

        String actual = new Saver().save(load);

        assertEquals(expected, actual);
    }
}
