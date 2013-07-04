package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Something;

import java.util.List;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:52
 */
public interface TerritoryMap {

    List<Something> getSomethingNear(Me me);

    Something getAt(Point point);

    boolean isNear(Me me, Something object);

    boolean isOutOfWorld(Point point);

    void openSpace(Me me);

    String getViewArea(Me me);

    List<Something> getAllNear(Me me);
}
