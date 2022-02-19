package apofig.javaquest.field;

import apofig.javaquest.field.object.*;
import apofig.javaquest.field.object.impl.Stone;
import apofig.javaquest.field.object.impl.StoneForum;
import apofig.javaquest.field.object.impl.dron.DronMentor;
import apofig.javaquest.field.object.monster.Monster;
import apofig.javaquest.field.object.monster.MonsterFactory;
import apofig.javaquest.field.object.monster.MonsterPool;
import org.fest.reflect.core.Reflection;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static apofig.javaquest.field.Messages.withoutSeparator;
import static junit.framework.Assert.*;
import static org.fest.reflect.core.Reflection.field;

public class JavaQuestTest {

    private JavaQuest game;
    private TerritoryField territoryField;
    private Me player;
    private Me alien;
    private ObjectFactoryImpl objects;
    private int countMonsters = 0;
    private RectangleField field;

    public int getSize() {
        return 100;
    }

    public int getViewAreaSize() {
        return 13;
    }

    public RectangleField getFieldLoader() {
        return new RectangleField(getSize(), getSize());
    }

    @Before
    public void init() {
        field = getFieldLoader();

        Settings settings = new Settings() {
            @Override
            public int viewSize() {
                return JavaQuestTest.this.getViewAreaSize();
            }

            @Override
            public FieldLoader fieldLoader() {
                return field;
            }

            @Override
            public MonsterFactory monsters() {
                return new MonsterFactory() {
                    @Override
                    public MonsterPool newMonsters() {
                        return new MonsterPool() {
                            @Override
                            public Monster next() {
                                countMonsters++;
                                return new Monster("Сразись со мной!",
                                        "die!",
                                        "Я убью тебя!",
                                        "Просто так ты не уйдешь!",
                                        "немногоКода('для подсказки');", 0) {

                                    @Override
                                    public String getName() {
                                        return "Monster" + countMonsters;
                                    }
                                };

                            }
                        };
                    }
                };
            }
        };
        game = new JavaQuest(settings);
        objects = (ObjectFactoryImpl) field("objects").ofType(ObjectFactory.class).in(game).get();
        player = game.newPlayer("Player");
        territoryField = (TerritoryField) field("heroField").ofType(HeroField.class).in(game).get();
    }

    @Test
    public void testIAmOnField() {
        game.tick();

        asrtFld("╔══════════════════════════╗\n" +
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

    private void asrtFld(String expected) {
        assertEquals(expected, getField(player));
    }

    private void asrtFld(String expected, Me me) {
        assertEquals(expected, getField(me));
    }

    private String getField(Me me) {
        StringOutputStream out = new StringOutputStream();
        territoryField.printNear(me, out);
        return out.getResult();
    }

    @Test
    public void testIGoRight() {
        moveRight();

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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

        asrtFld("╔══════════════════════════╗\n" +
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
        asrtFld("╔══════════════════════════╗\n" +
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
    public void testICantActWall() {
        testICantGoOnBoardDown();

        player.attack("die!");

        assertMessage("Player: die!\n" +
                "Wall: Ты не можешь это делать со мной!");
    }

    @Test
    public void shouldOnlyOneMessagePerTick() {
        testICantGoOnBoardDown();

        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        assertMessage("");
    }

    @Test
    public void testICantGoOnBoardUp() {
        moveTo(20, getSize() - 1);
        moveUp();

        verifyXY(20, getSize() - 1);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtFld("╔══════════════════════════╗\n" +
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
        asrtFld("╔══════════════════════════╗\n" +
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
        verifyXY(player, x, y);
    }

    private void verifyXY(Me player, int x, int y) {
        assertEquals(x, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void testICantGoOnBoardRight() {
        moveTo(getSize() - 1, 20);
        moveRight();

        verifyXY(getSize() - 1, 20);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtFld("╔══════════════════════════╗\n" +
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
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 3, my);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    private void moveTo(int x, int y) {
        moveTo(player, x, y);
    }

    private void moveTo(Me player, int x, int y) {
        int count = 0;
        while (Math.abs(player.getX() - x) != 0 || Math.abs(player.getY() - y) != 0) {
            if (count++ > 1000) {
                throw new RuntimeException(String.format(
                        "Я не могу пройти сюда! My x=%s and y=%s, and view:\n%s",
                        player.getX(), player.getY(), territoryField.getViewArea(player)));
            }
            if (player.getY() < y) {
                moveUp(player);
            } else if (player.getY() > y) {
                moveDown(player);
            }
            if (player.getX() < x) {
                moveRight(player);
            } else if (player.getX() > x) {
                moveLeft(player);
            }
        }
        verifyXY(player, x, y);
    }

    private void moveRight() {
        moveRight(player);
    }

    private void moveLeft() {
        moveLeft(player);
    }

    private void moveUp() {
        moveUp(player);
    }

    private void moveDown() {
        moveDown(player);
    }

    @Test
    public void testWhenITalkWithMonster() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);


        assertMessage("Monster1: Сразись со мной!");

        assertCode("немногоКода('для подсказки');");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster() {
        // given
        givenGold(player, 15);

        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        assertMessage("Monster1: Сразись со мной!");

        // when
        // first attempt
        moveLeft();

        // then
        verifyXY(mx - 1, my);
        assertMessage("Monster1: Просто так ты не уйдешь!");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
        assertGoldWithPortal(player, 15, "92866694");

        // when
        // second attempt
        moveLeft();

        // then
        verifyXY(mx - 2, my);
        assertMessage("Monster1: Монстр отнял у тебя немного золота...");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I   @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertGold(player, 5);
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster_caseIfNotEnoughGold() {
        givenGold(player, 5);

        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        // first attempt
        moveLeft();
        // second attempt
        moveLeft();

        assertMessage("Monster1: Сразись со мной!\n" +
                "Monster1: Просто так ты не уйдешь!\n" +
                "Monster1: Монстр отнял у тебя немного золота...");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I   @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertGold(player, 0);

        // проверяем, что монстр таки збрал не 10 а 5
        moveRight();
        assertMessage("Monster1: Сразись со мной!");

        attack("die!");

        assertMessage("Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 15$");
    }

    @Test
    public void shouldGetAllGoldFromMonster() {
        shouldNoMoveWhenITalkWithMonster();

        moveRight();
        assertMessage("Monster1: Сразись со мной!");

        attack("die!");

        assertMessage("Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 20$");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
        assertGold(player, 5);

        // get 20 gold
        moveRight();

        assertMessage("Gold: Привет, я - 20$\n" + // TODO почему-то говорит что оно золото 2 раза
                "Gold: Ты подобрал меня! Спасибо!!");
        assertGold(player, 25);
    }


    @Test
    public void shouldNoMoreTHanOneMessageWhenTick() {
        shouldNoMoveWhenITalkWithMonster();

        game.tick();
        game.tick();
        game.tick();

        assertMessage("");
    }

    @Test
    public void shouldKillMonsterWhenAttack() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("die!");

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttack() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("No!!!");

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: No!!!\n" +
                "Monster1: Я убью тебя!");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    private void attack(String message) {
        player.attack(message);
        game.tick();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttackTwice() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("No!!!");
        attack("Nooooo!!!");

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: No!!!\n" +
                "Monster1: Я убью тебя!\n" +
                "Player: Nooooo!!!\n" +
                "Monster1: Я убью тебя!");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoMoveWhenITryToGoOnWall() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setWall(mx, my);
        moveTo(mx - 1, my);

        moveRight();

        verifyXY(mx - 1, my);
        assertMessage("Wall: Пожалуйста, остановись!");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I #     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldGetGoldAfterMonsterDie() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 2, my);

        assertGold(player, 0);

        moveRight();

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");

        attack("die!");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");

        moveRight();

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ты подобрал меня! Спасибо!!");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");

        assertGold(player, 10);
    }

    private void assertInfo(Me player, String info) {
        assertEquals(info, player.getInfo().toString());
    }

    private void assertInfo(String info) {
        assertInfo(player, info);
    }

    private void assertMessage(String message) {
        assertMessage(player, message);
    }

    private void assertMessage(Me me, String message) {
        assertEquals(message, withoutSeparator(me.getMessenger().getMessages().get()));
        clearMessages(me);
    }

    private void clearMessages(Me me) {
        me.getMessenger().getMessages().clear();
    }

    @Test
    public void shouldLeaveGoldAfterMonsterDie() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("die!");

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoRepeatMessageAgain() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("die!");
        moveUp();

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ну и ладно! Достанусь кому-то другому!!");

        asrtFld("╔══════════════════════════╗\n" +
                "║????????????????      ????║\n" +
                "║                          ║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldNoKillGold() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("die!");
        attack("Gold die!");

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Player: Gold die!\n" +
                "Gold: Ты не можешь делать это со мной!");
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldSomeMessageAfterGetGold() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("die!");
        moveRight();  // get gold
        moveRight();  // go away

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ты подобрал меня! Спасибо!!");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldGoAwayFromGold() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);
        moveTo(mx - 1, my);

        attack("die!");
        moveLeft();

        assertMessage("Monster1: Сразись со мной!\n" +
                "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ну и ладно! Достанусь кому-то другому!!");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
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
                "╚══════════════════════════╝");
    }

    @Test
    public void shouldTwoPlayersOnFieldWhenCreateSecondPlayer() {
        moveDown();
        moveDown();
        moveDown();

        asrtFld("╔══════════════════════════╗\n" +
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
                "╚══════════════════════════╝", player);

        Me anotherMe = game.newPlayer("Alien");
        moveLeft(anotherMe);
        moveLeft(anotherMe);
        moveLeft(anotherMe);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??    A                 ??║\n" +
                "║??                      ??║\n" +
                "║??                      ??║\n" +
                "║??          I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "╚══════════════════════════╝", player);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????            ??????????║\n" +
                "║                    ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║      I                 ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║            A         ????║\n" +
                "║                    ??????║\n" +
                "║????            ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝", anotherMe);
    }

    @Test
    public void shouldStopWhenMeetWithAnother() {
        moveLeft();
        moveLeft();
        moveLeft();

        asrtFld("╔══════════════════════════╗\n" +
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
                "╚══════════════════════════╝");

        alien = game.newPlayer("Alien");
        moveLeft(alien);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????            ??????????║\n" +
                "║                    ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║      I   A             ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║????            ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        moveLeft(alien);    // move

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????            ??????????║\n" +
                "║                    ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║      I A               ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║????            ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(alien,
                "Player: Привет, я такой же как и ты игрок!\n" +
                "Alien: Привет, я такой же как и ты игрок!");
        assertMessage(player,
                "Player: Привет, я такой же как и ты игрок!\n" +
                "Alien: Привет, я такой же как и ты игрок!");

        moveLeft(alien);    // dont move
        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║????            ??????????║\n" +
                "║                    ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║      I A               ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                    ??????║\n" +
                "║????            ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(alien, "Player: Привет, я такой же как и ты игрок!");
        assertMessage(player, "Player: Привет, я такой же как и ты игрок!");

        game.tick();
        game.tick();

        assertMessage(alien, "");
        assertMessage(player, "");
    }

    @Test
    public void shouldNoMessagesWhenLeaveOneAnother() {
        moveLeft();
        moveLeft();

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????          ??????????║\n" +
                "║??                  ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║        I               ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║??                  ??????║\n" +
                "║??????          ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        alien = game.newPlayer("Alien");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????          ??????????║\n" +
                "║??                  ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║        I   A           ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║??                  ??????║\n" +
                "║??????          ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        moveLeft(alien);    // meet

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????          ??????????║\n" +
                "║??                  ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║        I A             ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║??                  ??????║\n" +
                "║??????          ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(alien,
                "Player: Привет, я такой же как и ты игрок!\n" +
                "Alien: Привет, я такой же как и ты игрок!");
        assertMessage(player,
                "Player: Привет, я такой же как и ты игрок!\n" +
                "Alien: Привет, я такой же как и ты игрок!");

        moveRight(alien);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????          ??????????║\n" +
                "║??                  ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║        I   A           ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║??                  ??????║\n" +
                "║??????          ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(alien, "Player: Ну пока!");
        assertMessage(player, "Player: Ну пока!");

        alien.attack("Some message!");

        assertMessage(alien, "Alien: Some message!");
        assertMessage(player, "");

        player.attack("Another message!");

        assertMessage(alien, "");
        assertMessage(player, "Player: Another message!");
    }

    private void moveRight(Me player) {
        player.moveRight();
        game.tick();
    }

    private void moveDown(Me player) {
        player.moveDown();
        game.tick();
    }

    private void moveUp(Me player) {
        player.moveUp();
        game.tick();
    }

    @Test
    public void shouldChatBetweenTwoPlayers() {
        moveLeft();
        moveLeft();

        alien = game.newPlayer("Alien");

        moveLeft(alien);    // meet

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????          ??????????║\n" +
                "║??                  ??????║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║                        ??║\n" +
                "║        I A             ??║\n" +
                "║                        ??║\n" +
                "║                      ????║\n" +
                "║                      ????║\n" +
                "║??                  ??????║\n" +
                "║??????          ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(alien,
                "Player: Привет, я такой же как и ты игрок!\n" +
                "Alien: Привет, я такой же как и ты игрок!");
        assertMessage(player,
                "Player: Привет, я такой же как и ты игрок!\n" +
                "Alien: Привет, я такой же как и ты игрок!");

        alien.attack("Message 1");

        assertMessage(alien, "Alien: Message 1");
        assertMessage(player, "Alien: Message 1");

        alien.attack("Message 2");

        assertMessage(alien, "Alien: Message 2");
        assertMessage(player, "Alien: Message 2");

        player.attack("Message 3");

        assertMessage(alien, "Player: Message 3");
        assertMessage(player, "Player: Message 3");
    }

    private String objects() {
        List<String> result = new ArrayList<String>();
        for (Something smth : objects.getObjects()) {
            result.add(Reflection.field("world").ofType(World.class).in(smth).get().toString());
        }
        return result.toString();
    }

    @Test
    public void shouldMeetWithDronMentor() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setDronMentor(mx, my);
        field.setGold(mx, my + 2);
        field.setGold(mx, my + 3);

        field.set(mx, my + 5, '#');

        moveTo(mx - 2, my + 2);

        moveDown();
        moveDown();

        moveRight();

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                  I M     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "DronMentor: " + DronMentor.MESSAGE);

        assertTrue(objects(), objects().contains("[object DronMentor at field[40,20]='M']"));

        player.attack("Ok!");

        assertFalse(objects.toString().contains("[object DronMentor"));

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                  I *     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player,
                "Player: Ok!\n" +
                "Dron: Я твой робот! Запрограммируй меня.");

        assertCode("public String whereToGo(String nearMe) {\n" +
                "    return \"|\";\n" +
                "}");

        player.attack("command");

        assertMessage(player,
                "Player: command\n" +
                "Dron: Команда принята! Обработка начнется после того как ты отойдешь.");

        moveLeft();

        assertMessage(player,
                "Dron: Обработка началась!\n" +
                "Dron: Команда 'command' не принята! Остановка!!!");

        moveRight();

        assertMessage(player,
                "Dron: Я твой робот! Запрограммируй меня.");

        assertCode("command");

        player.attack("up");

        assertMessage(player,
                "Player: up\n" +
                "Dron: Команда принята! Обработка начнется после того как ты отойдешь.");

        moveLeft();

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                    $     ║\n" +
                "║                    *     ║\n" +
                "║                I         ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertMessage(player, "Dron: Обработка началась!");
        assertGold(player, 0);

        game.tick();

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                    *     ║\n" +
                "║                          ║\n" +
                "║                I         ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertMessage(player, "Dron: Дрон подобрал золото!");
        assertGold(player, 1);

        game.tick();

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                          ║\n" +
                "║                    *     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I         ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertMessage(player, "Dron: Дрон подобрал золото!");
        assertGold(player, 2);

        game.tick();

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                    *     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I         ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertMessage(player, "");

        game.tick();

        asrtFld("╔══════════════════════════╗\n" +
                "║                        ??║\n" +
                "║                    #   ??║\n" +
                "║                    *     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I         ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║??????????                ║\n" +
                "║??????????????        ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");
        assertMessage(player, "Dron: Дрон уперся в неопознанный объект! Остановка!!!");

        game.tick();
        assertMessage(player, "");

        moveRight();
        moveRight();
        moveUp();
        moveUp();
        moveUp();

        asrtFld("╔══════════════════════════╗\n" +
                "║                          ║\n" +
                "║                  #       ║\n" +
                "║                  *       ║\n" +
                "║                  I       ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║????????                  ║\n" +
                "║????????????          ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "Dron: Я твой робот! Запрограммируй меня.");

        assertCode("up");

        player.attack("badCommand");
        clearMessages(player);

        moveDown();
        assertMessage(player, "Dron: Обработка началась!\n" +
                "Dron: Команда 'badCommand' не принята! Остановка!!!");
    }

    @Test
    public void shouldMeetWithStone() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setStone(mx, my);
        moveTo(mx - 1, my);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I O     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "Stone: " + Stone.MESSAGE_INTRO);

        player.attack("");

        assertMessage("Stone: " + Stone.MESSAGE_1);

        player.attack("");

        assertMessage("Stone: " + Stone.MESSAGE_2);

        player.attack("");

        assertMessage(player, "");

        moveDown();

        assertMessage(player, "Stone: " + Stone.MESSAGE_GOODBYE);

        moveUp();

        assertMessage(player, "Stone: " + Stone.MESSAGE_INTRO);

        player.attack("");

        assertMessage("Stone: " + Stone.MESSAGE_1);

        moveDown();

        assertMessage(player, "Stone: " + Stone.MESSAGE_GOODBYE);

        moveUp();

        assertMessage(player, "Stone: " + Stone.MESSAGE_INTRO);

        player.attack("");

        assertMessage("Stone: " + Stone.MESSAGE_1);
    }

    @Test
    public void shouldAnyUserHasTheirMonsterPool() {
        moveRight();
        moveRight();
        moveRight();

        alien = game.newPlayer("Alien");

        int mx1 = player.getX() + 10;
        int my1 = player.getY();
        field.setMonster(mx1, my1);

        int mx2 = player.getX() + 10;
        int my2 = player.getY() + 4;
        field.setMonster(mx2, my2);

        int mx3 = player.getX() + 10;
        int my3 = player.getY() - 4;
        field.setMonster(mx3, my3);

        moveTo(mx1 - 1, my1);
        moveTo(alien, mx2 - 2, my2);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                A   @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    @     ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "Monster1: Сразись со мной!");
        assertMessage(alien, "");

        moveRight(alien);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                  A @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    @     ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "");
        assertMessage(alien, "Monster2: Сразись со мной!");

        player.attack("die!");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                  A @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    @     ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "Player: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMessage(alien, "");

        moveLeft(player);
        assertMessage(player, "Gold: Ну и ладно! Достанусь кому-то другому!!");
        moveDown(player);
        moveDown(player);
        moveDown(player);
        moveDown(player);

        asrtFld("╔══════════════════════════╗\n" +
                "║                      ????║\n" +
                "║                  A @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                I   @     ║\n" +
                "║                          ║\n" +
                "║????????                  ║\n" +
                "║????????                  ║\n" +
                "╚══════════════════════════╝");

        moveRight(player);

        asrtFld("╔══════════════════════════╗\n" +
                "║                      ????║\n" +
                "║                  A @     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║????????                  ║\n" +
                "║????????                  ║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "Monster3: Сразись со мной!");
        assertMessage(alien, "");

        alien.attack("die!");

        asrtFld("╔══════════════════════════╗\n" +
                "║                      ????║\n" +
                "║                  A $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║????????                  ║\n" +
                "║????????                  ║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "");
        assertMessage(alien, "Alien: die!\n" +
                "Monster2: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");

        moveDown(alien);
        moveDown(alien);
        moveRight(alien);
        moveRight(alien);
        assertMessage(alien, "Gold: Ну и ладно! Достанусь кому-то другому!!");
        moveDown(alien);
        moveDown(alien);
        assertMessage(alien, "Gold: Привет, я - 10$");

        asrtFld("╔══════════════════════════╗\n" +
                "║                      ????║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    $ A   ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @     ║\n" +
                "║                          ║\n" +
                "║????????                  ║\n" +
                "║????????                  ║\n" +
                "╚══════════════════════════╝");

        moveDown(alien);
        moveDown(alien);
        assertMessage(alien, "Gold: Ну и ладно! Достанусь кому-то другому!!");
        moveDown(alien);
        moveDown(alien);

        asrtFld("╔══════════════════════════╗\n" +
                "║                      ????║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    $     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I @ A   ║\n" +
                "║                          ║\n" +
                "║????????                  ║\n" +
                "║????????                  ║\n" +
                "╚══════════════════════════╝");

        assertMessage(player, "");
        assertMessage(alien, "Monster3: Я сейчас занят!");

        player.attack("die!");

        assertMessage(player, "Player: die!\n" +
                "Monster3: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMessage(alien, "");
    }

    private void assertCode(String expected) {
        assertEquals(expected, game.getCodeHelper(player).getCode());
    }

    private void moveLeft(Me anotherMe) {
        anotherMe.moveLeft();
        game.tick();
    }

    @Test
    public void shouldMonsterBusyWhenFightWithAlien() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.setMonster(mx, my);

        moveLeft();
        moveLeft();
        moveLeft();

        alien = game.newPlayer("Alien");

        moveTo(alien, mx - 1, my);

        assertMessage(alien, "Monster1: Сразись со мной!");

        moveTo(mx - 3, my - 2);
        moveRight();
        moveRight();
        moveRight();
        moveRight();
        moveRight();
        moveUp();
        moveUp();

        assertMessage(player, "");
        assertMessage(alien, "");

        moveLeft(player);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????????        ????║\n" +
                "║??????????                ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║            A @ I         ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "╚══════════════════════════╝");

        assertMessage(alien, "");
        assertMessage(player, "Monster1: Я сейчас занят!");
    }

    @Test
    public void shouldWhenBusyMonsterDieAnotherDontKnowThisFact() {
        shouldMonsterBusyWhenFightWithAlien();

        alien.attack("die!");

        assertMessage(alien, "Alien: die!\n" +
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMessage(player, "");
    }

    @Test
    public void shouldCanKillAnotherBusyMonster() { // TODO а может не стоит разрешать помагать?
        shouldMonsterBusyWhenFightWithAlien();

        player.attack("die!");

        assertMessage(player, "Player: die!");
        assertMessage(alien,
                "Monster1: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
    }


    @Test
    public void shouldPlayerCanLeaveBusyMonster() {
        shouldMonsterBusyWhenFightWithAlien();

        moveRight(player);

        assertMessage(alien, "");
        assertMessage(player, "");
    }

    @Test
    public void shouldPlayerCantLeaveMonsterWhenOtherMeetWith() {
        // given
        shouldMonsterBusyWhenFightWithAlien();

        givenGold(player, 15);
        givenGold(alien, 15);

        assertGold(player, 15);
        assertGoldWithPortal(alien, 15, "BB420B06");

        // when
        moveLeft(alien);

        // then
        assertMessage(alien, "Monster1: Просто так ты не уйдешь!");
        assertMessage(player, "");

        assertGold(player, 15);
        assertGoldWithPortal(alien, 15, "BB420B06");

        // when
        // TODO почему я вернулся тут вправо? я что в прошлой итерации таки ушел от монстра?
        moveRight(player);

        // then
        assertMessage(alien, "");
        assertMessage(player, "");

        assertGold(player, 15);
        assertGoldWithPortal(alien, 15, "BB420B06");

        // when
        moveLeft(alien);

        // then
        assertMessage(alien, "Monster1: Монстр отнял у тебя немного золота...");
        assertMessage(player, "");

        assertGold(player, 15);
        assertGold(alien, 5);
    }

    private void assertGold(Me player, int amount) {
        assertInfo(player, "Уровень:0 Опыт:0 Здоровье:100 Золото:" + amount);
    }

    private void assertGoldWithPortal(Me player, int amount, String portal) {
        assertInfo(player, "Уровень:0 Опыт:0 Здоровье:100 Золото:" + amount + " Портал: " + portal);
    }

    private void givenGold(Me player, int amount) {
        player.filchGold(-amount);
    }

    @Test
    public void shouldUserRemovedWhenRemoveItFromGame() {
        Me alien = game.newPlayer("Alien");

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??                      ??║\n" +
                "║??        A I           ??║\n" +
                "║??                      ??║\n" +
                "║????                  ????║\n" +
                "║????                  ????║\n" +
                "║??????              ??????║\n" +
                "║??????????      ??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝", alien);

        game.remove(player.getName());
        game.tick();

        try {
            asrtFld("");
            fail("");
        } catch (IllegalArgumentException e) {
            assertEquals("Нет такого пользователя!", e.getMessage());
        }


        asrtFld("╔══════════════════════════╗\n" +
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
                "╚══════════════════════════╝", alien);
    }

    @Test
    public void shuoldCantRemoveAlreadyRemovedUser() {
        game.remove(player.getName());

        try {
            game.remove(player.getName());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Игрок с именем 'Player' не найден", e.getMessage());
        }
    }

    @Test
    public void shouldNoAddUserThatAlreadyRegistered() {
        try {
            game.newPlayer(player.getName());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Игрок с именем 'Player' уже зарегистрирован", e.getMessage());
        }
    }

    @Test
    public void shouldIfIMeetWithStoneOtherObjects() {
        int mx = player.getX() + 10;
        int my = player.getY();
        field.setStone(mx, my);
        field.setWall(mx, my + 1);
        field.setWall(mx, my - 1);
        moveTo(mx - 1, my);

        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                    #     ║\n" +
                "║                  I O     ║\n" +
                "║                    #     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage("Stone: " + Stone.MESSAGE_INTRO);

        player.attack("ok");

        assertMessage("Player: ok\n" +
                "Stone: " + Stone.MESSAGE_1);
    }

    @Test
    public void shouldWriteSomethingOnBoardForum() {
        int mx = player.getX() + 20;
        int my = player.getY();
        field.set(mx, my, '0');
        moveTo(mx - 1, my);


        asrtFld("╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║                      ????║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                  I 0     ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                          ║\n" +
                "║                      ????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝");

        assertMessage("StoneForum: " + StoneForum.MESSAGE_INTRO);

        player.attack("");

        assertMessage("StoneForum: " + StoneForum.MESSAGE_LAST);

        player.attack("Тут был Вася");

        assertMessage("Player: Тут был Вася");

        player.attack("");

        assertMessage("StoneForum: " + StoneForum.MESSAGE_LAST);

        player.attack("И Васе тут понравилось!");

        assertMessage("Player: И Васе тут понравилось!");

        moveLeft();
        moveRight();

        assertMessage("StoneForum: " + StoneForum.MESSAGE_INTRO);

        player.attack("");

        assertMessage("StoneForum: Тут был Вася");

        player.attack("");

        assertMessage("StoneForum: И Васе тут понравилось!");

        player.attack("");

        assertMessage("StoneForum: " + StoneForum.MESSAGE_LAST);
    }

}
