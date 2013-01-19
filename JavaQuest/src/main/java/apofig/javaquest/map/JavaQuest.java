package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:50 PM
 */
public class JavaQuest {
    TerritoryMap map = new TerritoryMap();

    public TerritoryMap getTerritoryMap() {
        return map;
    }

    public Joystick getPlayer() {
        return new Joystick() {
            @Override
            public void moveRight() {
                map.changePos(map.getX() + 1, map.getY());
            }

            @Override
            public void moveLeft() {
                map.changePos(map.getX() - 1, map.getY());
            }

            @Override
            public void moveUp() {
                map.changePos(map.getX(), map.getY() + 1);
            }
        };
    }
}
