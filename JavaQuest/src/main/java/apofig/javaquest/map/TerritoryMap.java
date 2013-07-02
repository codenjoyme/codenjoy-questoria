package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Something;

import java.io.OutputStream;
import java.util.List;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:52
 */
public interface TerritoryMap {

    List<Something> getSomethingNearMe();

    Something getAt(int x, int y);

    boolean isNear(int xx, int yy, Something object);

    String getViewArea();

    Me me();

    boolean isOutOfWorld(int x, int y);

    void openSpace();
}
