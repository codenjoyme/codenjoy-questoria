package apofig.javaquest.map;

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
        factory = new ObjectFactory() {
            @Override
            public Something make(char c, Place place) {
                ObjectSettings object = getObject(c);
                object.setPlace(place);
                object.setFactory(factory);
                object.setMessages(messages);
                return (Something)object;
            }
        };
        map = new TerritoryMap(settings.getMapLoader(), settings.getViewAreaSize(), factory);
    }

    private ObjectSettings getObject(char c) {
        if (c == ' ') {
            return new Nothing();
        } else if (c == '@') {
            return new Monster();
        } else if (c == '#') {
            return new Wall();
        } else if (c == '$') {
            return new Gold();
        }
        throw new UnsupportedOperationException("WTF! New object in world - " + c);
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
                for (Something smthNear : map.getSomethingNearMe()) {
                    messages.add("You: " + message);
                    smthNear.answer(message);
                }
            }
        };
    }

    private void tryToMove(int dx, int dy) {
        int x = map.getX() + dx;
        int y = map.getY() + dy;
        Something smthAtWay = map.getAt(x, y);
        smthAtWay.askMe();
        if (!smthAtWay.iCanUse()) {
            return;
        }

        for (Something smthNear : map.getSomethingNearMe()) {
            if (!smthNear.iCanLeave()) {
                smthNear.askMe();
                return;
            }
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
        return messages.toString();
    }

    public Player getPlayerInfo() {
        return info;
    }
}
