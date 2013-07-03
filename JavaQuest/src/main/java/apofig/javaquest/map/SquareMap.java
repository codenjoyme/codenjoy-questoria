package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:08 PM
 */
public class SquareMap extends RectangleMap implements MapLoader {

    public SquareMap(int size) {
        super(size, size);
    }
}
