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

    List<Something> getSomethingNear(Viewable me);

    Something getAt(Point point);

    boolean isNear(Viewable me, Something object);

    void openSpace(Viewable me);

    String getViewArea(Viewable me);

    List<Something> getAllNear(Viewable me);

    Map getMap();
}
