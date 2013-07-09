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
public class JavaQuestTest {

    private JavaQuest game;
    private TerritoryMapImpl map;
    private Me me;

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
            public int viewSize() {
                return JavaQuestTest.this.getViewAreaSize();
            }

            @Override
            public MapLoader mapLoader() {
                return mapLoader;
            }

            @Override
            public MonsterPool monsters() {
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
        me = game.newPlayer("You");
    }

    @Test
    public void testIAmOnMap() {
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

    private void asrtMap(String expected) {
        assertEquals(expected, getMap(me));
    }

    private void asrtMap(String expected, Me me) {
        assertEquals(expected, getMap(me));
    }

    private String getMap(Me me) {
        StringOutputStream out = new StringOutputStream();
        map.printNear(me, out);
        return out.getResult();
    }

    @Test
    public void testIGoRight() {
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
    public void testIGoRightTwice() {
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
    public void testIGoRightThreeTimes() {
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
    public void testIGoRightSevenTimes() {
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
    public void testIGoLeft() {
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
    public void testIGoLeftFourTimes() {
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
    public void testIGoUp() {
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
    public void testIGoUpTwice() {
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
    public void testIGoUpFiveTimes() {
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
    public void testIGoUpSixTimes() {
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
    public void testIGoDownTwice() {
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
    public void testIGoDownFourTimes() {
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
    public void testIGoDownFiveTimes() {
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
    public void testIGoDownSixTimes() {
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
    public void testCheckGoToBoardLeftDown() {
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
    public void testTryToOpenViewOnBoardLeftDown() {
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
    public void testCheckGoToBoardUpRight() {
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
    public void testTryToOpenViewOnBoardUpRight() {
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
    public void testICantGoOnBoardDown() {
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
    public void testICantGoOnBoardUp() {
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
    public void testICantGoOnBoardLeft() {
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
        assertEquals(x, me.getX());
        assertEquals(y, me.getY());
    }

    @Test
    public void testICantGoOnBoardRight() {
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
    public void testWhenMoveICanFindMonster() {
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

    private void moveTo(int x, int y) {
        int count = 0;
        while (Math.abs(me.getX() - x) != 0 || Math.abs(me.getY() - y) != 0) {
            if (count++ > 1000) {
                throw new RuntimeException(String.format(
                        "Я не могу пройти сюда! My x=%s and y=%s, and view:\n%s",
                        me.getX(), me.getY(), map.getViewArea(me)));
            }
            if (me.getY() < y) {
                moveUp();
            } else if (me.getY() > y) {
                moveDown();
            }
            if (me.getX() < x) {
                moveRight();
            } else if (me.getX() > x) {
                moveLeft();
            }
        }
        verifyXY(x, y);
    }

    private void moveLeft() {
        moveLeft(me);
    }

    private void moveUp() {
        me.moveUp();
        game.tick();
    }

    private void moveDown() {
        me.moveDown();
        game.tick();
    }

    @Test
    public void testWhenITalkWithMonster() {
        moveTo(getMonsterX() - 1, getMonsterY());

        assertMessage("Monster: Сразись со мной!");

        assertEquals("немногоКода('для подсказки');",
                game.getCodeHelper(me).getCode());

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
    public void shouldNoMoveWhenITalkWithMonster() {
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
    public void shouldKillMonsterWhenAttack() {
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
    public void shouldMonsterStillAliveWhenBadAttack() {
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
        me.attack(message);
        game.tick();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttackTwice() {
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
    public void shouldNoMoveWhenITryToGoOnWall() {
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
        me.moveRight();
        game.tick();
    }

    @Test
    public void shouldGetGoldAfterMonsterDie() {
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
        assertEquals(info, me.getInfo().toString());
    }

    private void assertMessage(String message) {
        assertEquals(message, withoutSeparator(me.getMessages().get()));
    }

    @Test
    public void shouldLeaveGoldAfterMonsterDie() {
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
    public void shouldNoRepeatMessageAgain() {
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
    public void shouldNoKillGold() {
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
    public void shouldSomeMessageAfterGetGold() {
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
    public void shouldGoAwayFromGold() {
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

    @Test
    public void shouldTwoPlayersOnMapWhenCreateSecondPlayer() {
        moveDown();
        moveDown();
        moveDown();

        String view1 =
                "╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "╚══════════════════════════╝";
        asrtMap(view1, me);

        Me anotherMe = game.newPlayer("Another");
        moveLeft(anotherMe);
        moveLeft(anotherMe);
        moveLeft(anotherMe);

        asrtMap(view1, me);
        asrtMap("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????            ??????????║\n" +
                "║                    ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║      I                 ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║????            ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝", anotherMe);
    }

    private void moveLeft(Me anotherMe) {
        anotherMe.moveLeft();
        game.tick();
    }

}
