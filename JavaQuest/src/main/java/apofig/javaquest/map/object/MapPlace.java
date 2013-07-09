package apofig.javaquest.map.object;

import apofig.javaquest.map.Map;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:50 AM
 */
public class MapPlace implements Place {
    private Map map;
    private final int x;
    private final int y;

    public MapPlace(Map map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public void update(char newChar) {
        map.set(x, y, newChar);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}
