package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:08 PM
 */
public class TestTerritoryRectangleMap extends TestTerritoryMap {

    public int getWidth() {
        return 100;
    }

    public int getHeight() {
        return 200;
    }

    public RectangleMap getMapLoader() {
        return new RectangleMap(getWidth(), getHeight());
    }

}
