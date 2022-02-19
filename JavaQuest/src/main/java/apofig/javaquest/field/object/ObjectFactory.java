package apofig.javaquest.field.object;

import apofig.javaquest.field.Locator;
import apofig.javaquest.services.Tickable;

public interface ObjectFactory extends Tickable {
    Something get(Place place, Me founder, Object params);

    void add(Me me);

    void remove(Me me);

    Locator getLocator();
}
