package apofig.javaquest.map;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 21:20
 */
public class PointImpl implements Point {
    private int x;
    private int y;

    public PointImpl(int x, int y) {
        this.x = x;
        this.y = y;
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
