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

    Something getAt(Point point, Me founder);

    boolean isNear(Viewable me, Something object);

    void openSpace(Viewable me);

    String getViewArea(Viewable me);

    List<Something> getNear(Viewable me);

    Map getMap();

    void remove(Viewable me);

    void init(Me me);
}
