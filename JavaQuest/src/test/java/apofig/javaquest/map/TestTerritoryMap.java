package apofig.javaquest.map;

import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

            @Override
            public MonsterFactory getMonsters() {
                return new MonsterFactory() {
                    @Override
                    public Monster next() {

                        return new Monster("Fight with me!",
                                "die!", "I'll kill you!", null);
                    }
                };
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
        assertMessage("Wall: Please stop!");
        verifyMap();
    }

    @Test
    public void testICantGoOnBoardUp() throws Exception {
        moveTo(20, getHeight() - 1);
        joystick.moveUp();

        verifyXY(20, getHeight() - 1);
        assertMessage("Wall: Please stop!");
        verifyMap();
    }

    @Test
    public void testICantGoOnBoardLeft() throws Exception {
        moveTo(0, 20);
        joystick.moveLeft();

        verifyXY(0, 20);
        assertMessage("Wall: Please stop!");
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
        assertMessage("Wall: Please stop!");
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

        assertMessage("Monster: Fight with me!");
        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.moveRight();
        joystick.moveRight();

        verifyXY(getMonsterX() - 1, getMonsterY());
        assertMessage("Monster: Fight with me!");
        verifyMap();
    }

    @Test
    public void shouldKillMonsterWhenAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");

        assertMessage("Monster: Fight with me!\n" +
                "You: die!\n" +
                "Monster: yOU @#& Ki$%@&^ll me $!@!\n" +
                "Gold: I am an 10$");
        verifyMap();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("No!!!");

        assertMessage("Monster: Fight with me!\n" +
                "You: No!!!\n" +
                "Monster: I'll kill you!");
        verifyMap();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttackTwice() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("No!!!");
        joystick.attack("Nooooo!!!");

        assertMessage("Monster: Fight with me!\n" +
                "You: No!!!\n" +
                "Monster: I'll kill you!\n" +
                "You: Nooooo!!!\n" +
                "Monster: I'll kill you!");
        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITryToGoOnWall() throws Exception {
        moveTo(getWallX() - 1, getWallY());
        joystick.moveRight();
        joystick.moveRight();

        verifyXY(getWallX() - 1, getWallY());
        assertMessage("Wall: Please stop!");
        verifyMap();
    }

    @Test
    public void shouldGetGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        assertInfo("Level:0 Xp:0 Health:100 Gold:0");

        joystick.attack("die!");
        joystick.moveRight();

        assertMessage("Gold: You pick up me! Thanks!!");
        verifyMap();

        assertInfo("Level:0 Xp:0 Health:100 Gold:10");
    }

    private void assertInfo(String info) {
        assertEquals(info, game.getPlayerInfo().toString());
    }

    private void assertMessage(String message) {
        assertEquals(message, game.getMessage());
    }

    @Test
    public void shouldLeaveGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");
        joystick.moveUp();

        assertMessage("Gold: I am an 10$");
        verifyMap();
    }

    @Test
    public void shouldNoKillGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");
        joystick.attack("Gold die!");

        assertMessage("Monster: Fight with me!\n" +
                "You: die!\n" +
                "Monster: yOU @#& Ki$%@&^ll me $!@!\n" +
                "Gold: I am an 10$\n" +
                "You: Gold die!\n" +
                "Gold: You can't do this!");
        verifyMap();
    }

    @Test
    public void shouldHideMessageAfterGetGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        joystick.attack("die!");
        joystick.moveRight();
        joystick.moveRight();

        assertMessage("");
        verifyMap();
    }

}
