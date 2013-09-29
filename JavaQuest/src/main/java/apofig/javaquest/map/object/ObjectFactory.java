package apofig.javaquest.map.object;

import apofig.javaquest.map.Locator;
import apofig.javaquest.map.Point;
import apofig.javaquest.services.Tickable;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:49 AM
 */
public interface ObjectFactory extends Tickable {
    Something get(Place place, Me founder);

    void add(Me me);

    void remove(Me me);

    Locator getLocator();
}
