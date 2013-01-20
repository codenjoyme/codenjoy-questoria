package apofig.javaquest.map;

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
                    map.writeMessage(object.answer(message));
                }
            }
        };
    }

    private void tryToMove(int dx, int dy) {
        int x = map.getX() + dx;
        int y = map.getY() + dy;
        Something smth = map.getAt(x, y);
        if (!smth.iCanUse()) {
            map.writeMessage(smth.askMe());
            return;
        }

        for (Something object : map.getSomethingNearMe()) {
            if (!object.iCanLeave()) {
                return;
            }
        }

        map.changePos(x, y);
        meetWith();
    }

    private void meetWith() {
        for (Something object : map.getSomethingNearMe()) {
            map.writeMessage(object.askMe());
        }
    }
}
