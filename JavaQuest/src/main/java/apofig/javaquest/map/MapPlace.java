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
    private int x;
    private int y;

    private MapPlace() {}

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
    public String near() {
        return "" + map.get(x - 1, y - 1) + map.get(x, y - 1) + map.get(x + 1, y - 1) +
                map.get(x + 1, y) +
                map.get(x + 1, y + 1) + map.get(x, y + 1) + map.get(x - 1, y + 1) +
                map.get(x - 1, y);
    }

    @Override
    public char near(int dx, int dy) {
        return map.get(x + dx, y + dy);
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public boolean isAt(Point point) {
        return x == point.getX() && y == point.getY();
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("map[%s,%s]='%s'", x, y, getChar());
    }

}
