package apofig.javaquest.map;

import com.sun.webpane.sg.prism.WCMediaPlayerImpl;
import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:08 PM
 */
public class TestTerritoryMap {

    private JavaQuest game;
    private TerritoryMap map;
    private Joystick joystick;

    @Before
    public void init() {
        game = new JavaQuest();
        map = game.getTerritoryMap();
        joystick = game.getPlayer();
    }

    @Test
    public void testIAmOnMap() throws Exception {
        verifyMap(map);
    }

    private void verifyMap(TerritoryMap map) throws Exception {
        StringOutputStream out = new StringOutputStream();
        map.printNearMe(out);
        Approvals.verify(out.getResult());
    }

    @Test
    public void testIGoRight() throws Exception {
        joystick.moveRight();

        verifyMap(map);
    }

    @Test
    public void testIGoLeft() throws Exception {
        joystick.moveLeft();

        verifyMap(map);
    }

    @Test
    public void testIGoUp() throws Exception {
        joystick.moveUp();

        verifyMap(map);
    }

    @Test
    public void testIGoDownTwice() throws Exception {
        joystick.moveDown();
        joystick.moveDown();

        verifyMap(map);
    }

    @Test
    public void testCheckGoToBoardLeftDown() throws Exception {
        for (int count = 0; count < 15; count++) {
            joystick.moveDown();
            joystick.moveLeft();
        }

        verifyMap(map);
    }

    @Test
    public void testTryToOpenViewOnBoardLeftDown() throws Exception {
        for (int count = 0; count < 19; count++) {
            joystick.moveDown();
            joystick.moveLeft();
        }

        verifyMap(map);
    }

    @Test
    public void testCheckGoToBoardUpRight() throws Exception {
        for (int count = 0; count < 74; count++) {
            joystick.moveUp();
            joystick.moveRight();
        }

        verifyMap(map);
    }

    @Test
    public void testTryToOpenViewOnBoardUpRight() throws Exception {
        for (int count = 0; count < 78; count++) {
            joystick.moveUp();
            joystick.moveRight();
        }

        verifyMap(map);
    }

    @Test
    public void testICantGoOnBoardDown() throws Exception {
        for (int count = 0; count < 1000; count++) {
            joystick.moveDown();
        }

        assertEquals(0, map.getY());
        verifyMap(map);
    }

    @Test
    public void testICantGoOnBoardUp() throws Exception {
        for (int count = 0; count < 1000; count++) {
            joystick.moveUp();
        }

        assertEquals(99, map.getY());
        verifyMap(map);
    }

    @Test
    public void testICantGoOnBoardLeft() throws Exception {
        for (int count = 0; count < 1000; count++) {
            joystick.moveLeft();
        }

        assertEquals(0, map.getX());
        verifyMap(map);
    }

    @Test
    public void testICantGoOnBoardRight() throws Exception {
        for (int count = 0; count < 1000; count++) {
            joystick.moveRight();
        }

        assertEquals(99, map.getX());
        verifyMap(map);
    }


}
