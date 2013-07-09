package apofig.javaquest.map;

import apofig.javaquest.map.Map;
import apofig.javaquest.map.object.Place;

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
    public char getChar() {
        return map.get(x, y);
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
