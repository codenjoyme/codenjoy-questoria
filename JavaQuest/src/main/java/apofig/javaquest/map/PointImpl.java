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
    public boolean isAt(Point point) {
        return point.getX() == x && point.getY() == y;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        return isAt((Point)o);
    }
}
