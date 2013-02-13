package apofig.javaquest.map.object;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:50 AM
 */
public class MapPlace implements Place {
    private char[][] map;
    private final int x;
    private final int y;

    public MapPlace(char[][] map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public void update(char newChar) {
        map[x][y] = newChar;
    }
}
