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

    public void setWall(int x, int y) {
        map.set(x, y, '#');
    }

    public void setDronMentor(int x, int y) {
        map.set(x, y, 'M');
    }

    public void setGold(int x, int y) {
        map.set(x, y, '$');
    }

    public void set(int x, int y, char c) {
        map.set(x, y, c);
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public Map map() {
        return map;
    }

    @Override
    public Point initPosition() {
        return new PointImpl(posx, posy);
    }

    @Override
    public int height() {
        return height;
    }
}
