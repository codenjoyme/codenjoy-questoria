package apofig.javaquest.map;

import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:50 PM
 */
public class JavaQuest {

    private TerritoryMap map;
    private Settings settings;

    public JavaQuest(Settings settings) {
        this.settings = settings;
        map = new TerritoryMap(settings.getMapLoader(), settings.getViewAreaSize());
    }

    public TerritoryMap getTerritoryMap() {
        return map;
    }

    public Joystick getPlayer() {
        return new Joystick() {
            @Override
            public void moveRight() {
                tryToMove(1, 0);
            }

            @Override
            public void moveLeft() {
                tryToMove(-1, 0);
            }

            @Override
            public void moveUp() {
                tryToMove(0, 1);
            }

            @Override
            public void moveDown() {
                tryToMove(0, -1);
            }

            @Override
            public void attack(String message) {
                for (Something object : map.getSomethingNearMe()) {
                    if (object instanceof Monster) {
                        map.writeMessage(object.say(message));
                    }
                }
            }
        };
    }

    private void tryToMove(int dx, int dy) {
        if (!isNearMonster()) {
            map.changePos(map.getX() + dx, map.getY() + dy);
            meetWith();
        }
    }

    private void meetWith() {
        if (isNearMonster()) {
            map.writeMessage("Fight with me!");
        }
    }

    private boolean isNearMonster() {
        List<Something> nearMe = map.getSomethingNearMe();
        for (Something object : nearMe) {
            if (object instanceof Monster) {
                return true;
            }
        }
        return false;
    }
}
