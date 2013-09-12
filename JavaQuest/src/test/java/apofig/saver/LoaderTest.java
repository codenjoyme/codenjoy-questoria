package apofig.saver;

import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import apofig.javaquest.services.PlayerServiceImpl;
import apofig.saver.dummy.ArrayOfArrayOfCharContainer;
import apofig.saver.dummy.ChildForIntContainer;
import apofig.saver.dummy.ClassWithInnerClass;
import apofig.saver.dummy.ClassWithStaticInnerClass;
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
        checkSaveAndLoad(new ArrayOfArrayOfCharContainer(6),
                "{\"objects\":[{\"id\":\"ArrayOfArrayOfCharContainer@0\",\"type\":\"apofig.saver.dummy.ArrayOfArrayOfCharContainer\",\"fields\":[{\"array\":\"char[][]@1\"}]},{\"id\":\"char[][]@1\",\"type\":\"[[C\",\"fields\":[\"char[]@2\",\"char[]@3\"]},{\"id\":\"char[]@2\",\"type\":\"[C\",\"fields\":[\"abc\"]},{\"id\":\"char[]@3\",\"type\":\"[C\",\"fields\":[\"qwe\"]}],\"main\":\"ArrayOfArrayOfCharContainer@0\"}");
    }

    @Test
    public void oneFieldInSuperClass() {
        checkSaveAndLoad(new ChildForIntContainer(1, 2),
                "{\"objects\":[{\"id\":\"ChildForIntContainer@0\",\"type\":\"apofig.saver.dummy.ChildForIntContainer\",\"fields\":[{\"a\":\"1\"},{\"b\":\"2\"}]}],\"main\":\"ChildForIntContainer@0\"}");
    }

    @Test
    public void classWithInnerClass() {
        ClassWithInnerClass object = new ClassWithInnerClass(null);
        ClassWithInnerClass.Inner inner = object.new Inner(2);
        object.a = inner;

        checkSaveAndLoad(object,
                "{\"objects\":[{\"id\":\"ClassWithInnerClass@0\",\"type\":\"apofig.saver.dummy.ClassWithInnerClass\",\"fields\":[{\"a\":\"Inner@1\"}]},{\"id\":\"Inner@1\",\"type\":\"apofig.saver.dummy.ClassWithInnerClass$Inner\",\"fields\":[{\"this$0\":\"ClassWithInnerClass@0\"},{\"b\":\"2\"}]}],\"main\":\"ClassWithInnerClass@0\"}");
    }

    @Test
    public void classWithStaticInnerClass() {
        ClassWithStaticInnerClass object = new ClassWithStaticInnerClass(new ClassWithStaticInnerClass.Inner(4));

        checkSaveAndLoad(object,
                "{\"objects\":[{\"id\":\"ClassWithStaticInnerClass@0\",\"type\":\"apofig.saver.dummy.ClassWithStaticInnerClass\",\"fields\":[{\"a\":\"Inner@1\"}]},{\"id\":\"Inner@1\",\"type\":\"apofig.saver.dummy.ClassWithStaticInnerClass$Inner\",\"fields\":[{\"b\":\"4\"}]}],\"main\":\"ClassWithStaticInnerClass@0\"}");
    }

    private void checkSaveAndLoad(Object object, String expected) {
        String saved = new Saver().save(object);
        assertEquals(expected, saved);

        String loaded = new Saver().save(new Loader().load(expected));
        assertEquals(saved, loaded);
    }
}
