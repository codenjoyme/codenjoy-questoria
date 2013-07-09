package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.monster.Monster;
import apofig.javaquest.map.object.monster.MonsterPool;
import org.junit.Before;
import org.junit.Test;

import static apofig.javaquest.map.Messages.withoutSeparator;
import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:08 PM
 */
public class TestTerritoryMap {

    private JavaQuest game;
    private TerritoryMapImpl map;
    private Joystick joystick;

    public int getSize() {
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
        return new RectangleMap(getSize(), getSize());
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
            public MonsterPool getMonsters() {
                return new MonsterPool() {
                    @Override
                    public Monster next() {

                        return new Monster("Сразись со мной!",
                                "die!",
                                "Я убью тебя!",
                                "Никуда ты не уйдешь!",
                                "немногоКода('для подсказки');", null);

                    }
                };
            }
        };
        game = new JavaQuest(settings);
        map = (TerritoryMapImpl)game.getTerritoryMap();
        joystick = game.getMe();
    }

    @Test
    public void testIAmOnMap() throws Exception {
        game.tick();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??????              ??????║\n" +
                "║??????????      ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    private void asrtMap(String expected) throws Exception {
        assertEquals(expected, getMap());
    }

    private String getMap() {
        StringOutputStream out = new StringOutputStream();
        map.printNear(me(), out);
        return out.getResult();
    }

    @Test
    public void testIGoRight() throws Exception {
        moveRight();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????        ????????║\n" +
                "║??????                ????║\n" +
                "║????                    ??║\n" +
                "║????                    ??║\n" +
                "║??                        ║\n" +
                "║??            I           ║\n" +
                "║??                        ║\n" +
                "║????                    ??║\n" +
                "║????                    ??║\n" +
                "║??????                ????║\n" +
                "║??????????        ????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoRightTwice() throws Exception {
        moveRight();
        moveRight();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????          ??????║\n" +
                "║??????                  ??║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║??                        ║\n" +
                "║??              I         ║\n" +
                "║??                        ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║??????                  ??║\n" +
                "║??????????          ??????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoRightThreeTimes() throws Exception {
        moveRight();
        moveRight();
        moveRight();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????            ????║\n" +
                "║??????                    ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║??                        ║\n" +
                "║??                I       ║\n" +
                "║??                        ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║??????                    ║\n" +
                "║??????????            ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoRightSevenTimes() throws Exception {
        moveRight();
        moveRight();
        moveRight();
        moveRight();
        moveRight();
        moveRight();
        moveRight();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??                    ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I       ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??                    ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoLeft() throws Exception {
        moveLeft();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????????        ??????????║\n" +
                "║????                ??????║\n" +
                "║??                    ????║\n" +
                "║??                    ????║\n" +
                "║                        ??║\n" +
                "║          I             ??║\n" +
                "║                        ??║\n" +
                "║??                    ????║\n" +
                "║??                    ????║\n" +
                "║????                ??????║\n" +
                "║????????        ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoLeftFourTimes() throws Exception {
        moveLeft();
        moveLeft();
        moveLeft();
        moveLeft();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????              ????????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║                        ??║\n" +
                "║                          ║\n" +
                "║      I                   ║\n" +
                "║                          ║\n" +
                "║                        ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║????              ????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoUp() throws Exception {
        moveUp();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??????              ??????║\n" +
                "║??????????      ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoUpTwice() throws Exception {
        moveUp();
        moveUp();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??????              ??????║\n" +
                "║??????????      ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoUpFiveTimes() throws Exception {
        moveUp();
        moveUp();
        moveUp();
        moveUp();
        moveUp();

        asrtMap("╔══════════════════════════╗\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??????              ??????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoUpSixTimes() throws Exception {
        moveUp();
        moveUp();
        moveUp();
        moveUp();
        moveUp();
        moveUp();

        asrtMap("╔══════════════════════════╗\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoDownTwice() throws Exception {
        moveDown();
        moveDown();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??????              ??????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoDownFourTimes() throws Exception {
        moveDown();
        moveDown();
        moveDown();
        moveDown();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoDownFiveTimes() throws Exception {
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();

        asrtMap("╔══════════════════════════╗\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testIGoDownSixTimes() throws Exception {
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();

        asrtMap("╔══════════════════════════╗\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "╚══════════════════════════╝");
    }


    @Test
    public void testCheckGoToBoardLeftDown() throws Exception {
        moveTo(5, 5);

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????                ║\n" +
                "║????????                  ║\n" +
                "║??????                    ║\n" +
                "║????                      ║\n" +
                "║??                        ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║      I                 ??║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║                  ????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testTryToOpenViewOnBoardLeftDown() throws Exception {
        moveTo(1, 1);

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????                ║\n" +
                "║????????                  ║\n" +
                "║??????                    ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║####                      ║\n" +
                "║####                      ║\n" +
                "║####                      ║\n" +
                "║####                      ║\n" +
                "║####  I                 ??║\n" +
                "║####                  ????║\n" +
                "║################??????????║\n" +
                "║################??????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testCheckGoToBoardUpRight() throws Exception {
        moveTo(getSize() - 6, getSize() - 6);

        asrtMap("╔══════════════════════════╗\n" +
                "║????????                  ║\n" +
                "║??????                    ║\n" +
                "║????                      ║\n" +
                "║??                I       ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║                  ????????║\n" +
                "║                ??????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testTryToOpenViewOnBoardUpRight() throws Exception {
        moveTo(getSize() - 2, getSize() - 2);

        asrtMap("╔══════════════════════════╗\n" +
                "║??????????################║\n" +
                "║??????????################║\n" +
                "║????                  ####║\n" +
                "║??                I   ####║\n" +
                "║                      ####║\n" +
                "║                      ####║\n" +
                "║                      ####║\n" +
                "║                      ####║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║                  ????????║\n" +
                "║                ??????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testICantGoOnBoardDown() throws Exception {
        moveTo(20, 0);
        moveDown();

        verifyXY(20, 0);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtMap("╔══════════════════════════╗\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??######################??║\n" +
                "║????##################????║\n" +
                "║????##################????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testICantGoOnBoardUp() throws Exception {
        moveTo(20, getSize() - 1);
        moveUp();

        verifyXY(20, getSize() - 1);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtMap("╔══════════════════════════╗\n" +
                "║????##################????║\n" +
                "║????##################????║\n" +
                "║??######################??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testICantGoOnBoardLeft() throws Exception {
        moveTo(0, 20);
        moveLeft();

        verifyXY(0, 20);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????##                    ║\n" +
                "║######                    ║\n" +
                "║######                    ║\n" +
                "║######                    ║\n" +
                "║######                    ║\n" +
                "║######I                   ║\n" +
                "║######                    ║\n" +
                "║######                    ║\n" +
                "║######                    ║\n" +
                "║######                    ║\n" +
                "║????##                    ║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    private void verifyXY(int x, int y) {
        assertEquals(x, me().getX());
        assertEquals(y, me().getY());
    }
    
    public Me me() {
        return (Me)game.getMe();
    }

    @Test
    public void testICantGoOnBoardRight() throws Exception {
        moveTo(getSize() - 1, 20);
        moveRight();

        verifyXY(getSize() - 1, 20);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                    ##????║\n" +
                "║                    ######║\n" +
                "║                    ######║\n" +
                "║                    ######║\n" +
                "║                    ######║\n" +
                "║                  I ######║\n" +
                "║                    ######║\n" +
                "║                    ######║\n" +
                "║                    ######║\n" +
                "║                    ######║\n" +
                "║                    ##????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void testWhenMoveICanFindMonster() throws Exception {
        moveTo(getMonsterX() - 3, getMonsterY());

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I     @ ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    private void moveTo(int x, int y) throws Exception {
        int count = 0;
        while (Math.abs(me().getX() - x) != 0 || Math.abs(me().getY() - y) != 0) {
            if (count++ > 1000) {
                throw new RuntimeException(String.format(
                        "Я не могу пройти сюда! My x=%s and y=%s, and view:\n%s",
                        me().getX(), me().getY(), map.getViewArea(me())));
            }
            if (me().getY() < y) {
                moveUp();
            } else if (me().getY() > y) {
                moveDown();
            }
            if (me().getX() < x) {
                moveRight();
            } else if (me().getX() > x) {
                moveLeft();
            }
        }
        verifyXY(x, y);
    }

    private void moveLeft() {
        joystick.moveLeft();
        game.tick();
    }

    private void moveUp() {
        joystick.moveUp();
        game.tick();
    }

    private void moveDown() {
        joystick.moveDown();
        game.tick();
    }

    @Test
    public void testWhenITalkWithMonster() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());

        assertMessage("Monster: Сразись со мной!");

        assertEquals("немногоКода('для подсказки');",
                game.getCodeHelper().getCode());

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        moveRight();

        verifyXY(getMonsterX() - 1, getMonsterY());
        assertMessage("Monster: Сразись со мной!\n" +
                "Monster: Никуда ты не уйдешь!");
        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldKillMonsterWhenAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("No!!!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: No!!!\n" +
                "Monster: Я убью тебя!");
        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    private void attack(String message) {
        joystick.attack(message);
        game.tick();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttackTwice() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("No!!!");
        attack("Nooooo!!!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: No!!!\n" +
                "Monster: Я убью тебя!\n" +
                "You: Nooooo!!!\n" +
                "Monster: Я убью тебя!");
        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoMoveWhenITryToGoOnWall() throws Exception {
        moveTo(getWallX() - 1, getWallY());
        moveRight();

        verifyXY(getWallX() - 1, getWallY());
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtMap("╔══════════════════════════╗\n" +
                "║??                        ║\n" +
                "║??                        ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║????                      ║\n" +
                "║????          I #         ║\n" +
                "║????                      ║\n" +
                "║??????                  ??║\n" +
                "║??????                  ??║\n" +
                "╚══════════════════════════╝");
    }

    private void moveRight() {
        joystick.moveRight();
        game.tick();
    }

    @Test
    public void shouldGetGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 2, getMonsterY());
        assertInfo("Уровень:0 Опыт:0 Здоровье:100 Золото:0");

        moveRight();

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        attack("die!");
        moveRight();

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ты подобрал меня! Спасибо!!");
        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I       ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertInfo("Уровень:0 Опыт:0 Здоровье:100 Золото:10");
    }

    private void assertInfo(String info) {
        assertEquals(info, game.getPlayerInfo().toString());
    }

    private void assertMessage(String message) {
        assertEquals(message, withoutSeparator(game.getMessage()));
    }

    @Test
    public void shouldLeaveGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoRepeatMessageAgain() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");
        moveUp();

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I       ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoKillGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");
        attack("Gold die!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "You: Gold die!\n" +
                "Gold: Ты не можешь делать это со мной!");
        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldSomeMessageAfterGetGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");
        moveRight();  // get gold
        moveRight();  // go away

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ты подобрал меня! Спасибо!!");

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I       ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldGoAwayFromGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");
        moveLeft();

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ну и ладно! Достанусь кому-то другому!!");

        asrtMap("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I   $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

}
