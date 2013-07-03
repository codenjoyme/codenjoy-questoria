package apofig.javaquest.map.object;

import apofig.javaquest.map.TerritoryMap;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:57
 */
public class Me {

    private int x;
    private int y;
    private TerritoryMap map;

    public Me(TerritoryMap map) {
        this.map = map;
        x = -1;
        y = -1;
    }

    public void moveTo(int x, int y) {
        if (map.isOutOfWorld(x, y)) {
            return;
        }
        this.x = x;
        this.y = y;
        map.openSpace(x, y);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
