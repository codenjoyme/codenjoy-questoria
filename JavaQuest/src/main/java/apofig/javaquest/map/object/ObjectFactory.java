package apofig.javaquest.map.object;

import apofig.javaquest.map.Point;
import apofig.javaquest.services.Tickable;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:49 AM
 */
public interface ObjectFactory extends Tickable {
    Something get(Place place);

    void add(Me me);

    boolean isAt(Something smth, Point point);
}
