package apofig.javaquest.map;

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

    public int getWidth() {
        return 100;
    }

    public int getHeight() {
        return 100;
    }

    public int getViewAreaSize() {
        return 13;
    }

    public int getMonsterX() {
        return 40;
    }

    public int getMonsterY() {
        return 22;
    }

    public int getWallX() {
        return 22;
    }

    public int getWallY() {
        return 10;
    }

    public RectangleMap getMapLoader() {
        return new SquareMap(getWidth());
    }

    @Before
    public void init() {
        final RectangleMap mapLoader = getMapLoader();
        mapLoader.setMonster(getMonsterX(), getMonsterY());
        mapLoader.setWall(getWallX(), getWallY());

        Settings settings = new Settings() {
            @Override
            public int getViewAreaSize() {
                return TestTerritoryMap.this.getViewAreaSize();
            }

            @Override
            public MapLoader getMapLoader() {
                return mapLoader;
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
        if (getHeight() > getWidth()) { // fix for TestTerritoryRectangleMap test
            moveTo(20, 20 + (getHeight() - getWidth()));
        }
        moveTo(getWidth() - 6, getHeight() - 6);

        verifyMap();
    }

    @Test
    public void testTryToOpenViewOnBoardUpRight() throws Exception {
        if (getHeight() > getWidth()) { // fix for TestTerritoryRectangleMap test
            moveTo(20, 20 + (getHeight() - getWidth()));
        }
        moveTo(getWidth() - 2, getHeight() - 2);

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
        moveTo(20, getHeight() - 1);
        joystick.moveUp();

        verifyXY(20, getHeight() - 1);
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
        moveTo(getWidth() - 1, 20);
        joystick.moveRight();

        verifyXY(getWidth() - 1, 20);
        verifyMap();
    }

    @Test
    public void testWhenMoveICanFindMonster() throws Exception {
        moveTo(getMonsterX() - 3, getMonsterY());

        verifyMap();
    }

    private void moveTo(int x, int y) throws Exception {
        int count = 0;
        while (Math.abs(map.getX() - x) != 0 || Math.abs(map.getY() - y) != 0) {
            if (count++ > 1000) {
//                verifyMap();
                throw new RuntimeException(String.format(
                        "I cant go there! My x=%s and y=%s, and view:\n%s",
                        map.getX(), map.getY(), map.getViewArea()));
            }
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
        moveTo(getMonsterX() - 1, getMonsterY());

        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.moveRight();
        joystick.moveRight();

        verifyXY(getMonsterX() - 1, getMonsterY());
        verifyMap();
    }

    @Test
    public void shouldKillMonsterWhenAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");

        verifyMap();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("No!!!");

        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITryToGoOnWall() throws Exception {
        moveTo(getWallX() - 1, getWallY());
        joystick.moveRight();
        joystick.moveRight();

        verifyXY(getWallX() - 1, getWallY());
        verifyMap();
    }

    @Test
    public void shouldGetGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");
        joystick.moveRight();

        verifyMap();
    }

    @Test
    public void shouldLeaveGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");
        joystick.moveUp();

        verifyMap();
    }

    @Test
    public void shouldNoKillGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");
        joystick.attack("Gold die!");

        verifyMap();
    }

}
