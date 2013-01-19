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
        Settings settings = new Settings() {
            @Override
            public int getWorldSize() {
                return 100;
            }

            @Override
            public int getViewAreaSize() {
                return 13;
            }
        };
        game = new JavaQuest(settings);
        map = game.getTerritoryMap();
        joystick = game.getPlayer();
    }

    @Test
    public void testIAmOnMap() throws Exception {
        verifyMap();
    }

    private void verifyMap() throws Exception {
        Approvals.verify(getMap());
    }

    private String getMap() {
        StringOutputStream out = new StringOutputStream();
        map.printNearMe(out);
        return out.getResult();
    }

    @Test
    public void testIGoRight() throws Exception {
        joystick.moveRight();

        verifyMap();
    }

    @Test
    public void testIGoLeft() throws Exception {
        joystick.moveLeft();

        verifyMap();
    }

    @Test
    public void testIGoUp() throws Exception {
        joystick.moveUp();

        verifyMap();
    }

    @Test
    public void testIGoDownTwice() throws Exception {
        joystick.moveDown();
        joystick.moveDown();

        verifyMap();
    }

    @Test
    public void testCheckGoToBoardLeftDown() throws Exception {
        moveTo(5, 5);

        verifyMap();
    }

    @Test
    public void testTryToOpenViewOnBoardLeftDown() throws Exception {
        moveTo(1, 1);

        verifyMap();
    }

    @Test
    public void testCheckGoToBoardUpRight() throws Exception {
        moveTo(94, 94);

        verifyMap();
    }

    @Test
    public void testTryToOpenViewOnBoardUpRight() throws Exception {
        moveTo(98, 98);

        verifyMap();
    }

    @Test
    public void testICantGoOnBoardDown() throws Exception {
        moveTo(20, 0);
        joystick.moveDown();

        verifyXY(20, 0);
        verifyMap();
    }

    @Test
    public void testICantGoOnBoardUp() throws Exception {
        moveTo(20, 99);
        joystick.moveUp();

        verifyXY(20, 99);
        verifyMap();
    }

    @Test
    public void testICantGoOnBoardLeft() throws Exception {
        moveTo(0, 20);
        joystick.moveLeft();

        verifyXY(0, 20);
        verifyMap();
    }

    private void verifyXY(int x, int y) {
        assertEquals(x, map.getX());
        assertEquals(y, map.getY());
    }

    @Test
    public void testICantGoOnBoardRight() throws Exception {
        moveTo(99, 20);
        joystick.moveRight();

        verifyXY(99, 20);
        verifyMap();
    }

    @Test
    public void testWhenMoveICanFindMonster() throws Exception {
        moveTo(37, 22);

        verifyMap();
    }

    private void moveTo(int x, int y) {
        while (Math.abs(map.getX() - x) != 0 || Math.abs(map.getY() - y) != 0) {
            if (map.getY() < y) {
                joystick.moveUp();
            } else if (map.getY() > y) {
                joystick.moveDown();
            }
            if (map.getX() < x) {
                joystick.moveRight();
            } else if (map.getX() > x) {
                joystick.moveLeft();
            }
        }
        verifyXY(x, y);
    }

    @Test
    public void testWhenITalkWithMonster() throws Exception {
        moveTo(39, 22);

        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster() throws Exception {
        moveTo(39, 22);
        joystick.moveRight();
        joystick.moveRight();

        verifyXY(39, 22);
    }


}
