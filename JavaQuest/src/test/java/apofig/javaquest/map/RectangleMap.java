package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 7:12 PM
 */
public class RectangleMap implements MapLoader {

    private Map map;
    private int posx;
    private int posy;
    private int width;
    private int height;

    public RectangleMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Map(width, height, ' ');

        posx = 20;
        posy = 20;
    }

    public void setMonster(int x, int y) {
        map.set(x, y, '@');
    }

    public void setWall(int wallX, int wallY) {
        map.set(wallX, wallY, '#');
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public int getPlayerX() {
        return posx;
    }

    @Override
    public int getPlayerY() {
        return posy;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
