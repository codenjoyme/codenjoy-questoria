package apofig.javaquest.map;

import apofig.javaquest.map.object.ObjectFactory;
import apofig.javaquest.map.object.ObjectFactoryImpl;
import apofig.javaquest.map.object.Something;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:50 PM
 */
public class JavaQuest {

    private TerritoryMap map;
    private Messages messages;
    private ObjectFactory factory;
    private Player info;

    public JavaQuest(Settings settings) {
        messages = new Messages();
        info = new Player();
        factory = new ObjectFactoryImpl(messages, settings.getMonsters());
        map = new TerritoryMap(settings.getMapLoader(), settings.getViewAreaSize(), factory);
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
                messages.add("You: " + message);
                if (map.getSomethingNearMe().isEmpty()) {
                    Something whereIAm = map.getAt(map.getX(), map.getY());
                    whereIAm.answer(message);
                }

                for (Something smthNear : map.getSomethingNearMe()) {
                    smthNear.answer(message);
                }
            }
        };
    }

    private void tryToMove(int dx, int dy) {
        int x = map.getX() + dx;
        int y = map.getY() + dy;

        for (Something smthNear : map.getSomethingNearMe()) {
            if (!smthNear.iCanLeave()) {
                smthNear.tryToLeave();
                return;
            }
        }

        for (Something smth : map.getSomethingNearMe()) {
            if (smth.iCanLeave()) {
                if (!map.isNear(x, y, smth)) {
                    smth.tryToLeave();
                }
            }
        }

        Something smthAtWay = map.getAt(x, y);
        smthAtWay.askMe();
        if (!smthAtWay.iCanUse()) {
            return;
        }
        smthAtWay.getBy(info);
        map.changePos(x, y);
        meetWith();
    }

    private void meetWith() {
        for (Something object : map.getSomethingNearMe()) {
            object.askMe();
        }
    }

    public String getMessage() {
        return messages.getLast(60);
    }

    public Player getPlayerInfo() {
        return info;
    }
}
