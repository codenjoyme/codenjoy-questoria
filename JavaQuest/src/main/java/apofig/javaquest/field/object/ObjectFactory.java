package apofig.javaquest.field.object;

import apofig.javaquest.field.Locator;
import apofig.javaquest.services.Tickable;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:49 AM
 */
public interface ObjectFactory extends Tickable {
    Something get(Place place, Me founder, Object params);

    void add(Me me);

    void remove(Me me);

    Locator getLocator();
}
