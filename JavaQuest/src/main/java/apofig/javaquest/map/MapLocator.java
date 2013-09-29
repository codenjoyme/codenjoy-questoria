package apofig.javaquest.map;


import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Something;

import java.util.List;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 20:42
 */
public interface MapLocator {

    Something getAt(Point point, Me founder);

    boolean isNear(Me founder, Something object);

    List<Something> getNear(Me founder);
}
