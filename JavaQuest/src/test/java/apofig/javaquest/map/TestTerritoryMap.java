package apofig.javaquest.map;

import apofig.javaquest.map.object.monster.Monster;
import apofig.javaquest.map.object.monster.MonsterPool;
import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Test;

import static apofig.javaquest.map.Messages.withoutSeparator;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

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
        moveRight();

        verifyMap();
    }

    @Test
    public void testIGoLeft() throws Exception {
        moveLeft();

        verifyMap();
    }

    @Test
    public void testIGoUp() throws Exception {
        moveUp();

        verifyMap();
    }

    @Test
    public void testIGoDownTwice() throws Exception {
        moveDown();
        moveDown();

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
        moveDown();

        verifyXY(20, 0);
        assertMessage("Wall: Пожалуйста, остановись!");
        verifyMap();
    }

    @Test
    public void testICantGoOnBoardUp() throws Exception {
        moveTo(20, getHeight() - 1);
        moveUp();

        verifyXY(20, getHeight() - 1);
        assertMessage("Wall: Пожалуйста, остановись!");
        verifyMap();
    }

    @Test
    public void testICantGoOnBoardLeft() throws Exception {
        moveTo(0, 20);
        moveLeft();

        verifyXY(0, 20);
        assertMessage("Wall: Пожалуйста, остановись!");
        verifyMap();
    }

    private void verifyXY(int x, int y) {
        assertEquals(x, map.getX());
        assertEquals(y, map.getY());
    }

    @Test
    public void testICantGoOnBoardRight() throws Exception {
        moveTo(getWidth() - 1, 20);
        moveRight();

        verifyXY(getWidth() - 1, 20);
        assertMessage("Wall: Пожалуйста, остановись!");
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
                throw new RuntimeException(String.format(
                        "Я не могу пройти сюда! My x=%s and y=%s, and view:\n%s",
                        map.getX(), map.getY(), map.getViewArea()));
            }
            if (map.getY() < y) {
                moveUp();
            } else if (map.getY() > y) {
                moveDown();
            }
            if (map.getX() < x) {
                moveRight();
            } else if (map.getX() > x) {
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

        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITalkWithMonster() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        moveRight();

        verifyXY(getMonsterX() - 1, getMonsterY());
        assertMessage("Monster: Сразись со мной!\n" +
                "Monster: Никуда ты не уйдешь!");
        verifyMap();
    }

    @Test
    public void shouldKillMonsterWhenAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        verifyMap();
    }

    @Test
    public void shouldMonsterStillAliveWhenBadAttack() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("No!!!");

        assertMessage("Monster: Сразись со мной!\n" +
                "You: No!!!\n" +
                "Monster: Я убью тебя!");
        verifyMap();
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
        verifyMap();
    }

    @Test
    public void shouldNoMoveWhenITryToGoOnWall() throws Exception {
        moveTo(getWallX() - 1, getWallY());
        moveRight();

        verifyXY(getWallX() - 1, getWallY());
        assertMessage("Wall: Пожалуйста, остановись!");
        verifyMap();
    }

    private void moveRight() {
        joystick.moveRight();
        game.tick();
    }

    @Test
    public void shouldGetGoldAfterMonsterDie() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        assertInfo("Уровень:0 Опыт:0 Здоровье:100 Золото:0");

        attack("die!");
        moveRight();

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ну и ладно! Достанусь кому-то другому!!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ты подобрал меня! Спасибо!!");
        verifyMap();

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

        verifyMap();
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

        verifyMap();
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
        verifyMap();
    }

    @Test
    public void shouldSomeMessageAfterGetGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");
        moveRight();
        moveRight();

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ну и ладно! Достанусь кому-то другому!!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ты подобрал меня! Спасибо!!");
        verifyMap();
    }

    @Test
    public void shouldGoAwayFromGold() throws Exception {
        moveTo(getMonsterX() - 1, getMonsterY());
        attack("die!");
        moveLeft();
        moveLeft();

        assertMessage("Monster: Сразись со мной!\n" +
                "You: die!\n" +
                "Monster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$\n" +
                "Gold: Ну и ладно! Достанусь кому-то другому!!");
        verifyMap();
    }

}
