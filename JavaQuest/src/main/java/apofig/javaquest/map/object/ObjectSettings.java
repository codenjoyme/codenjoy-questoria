package apofig.javaquest.map.object;

import apofig.javaquest.map.Messages;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 2:00 AM
 */
public interface ObjectSettings {
    void setPlace(Place place);

    void setFactory(ObjectFactory factory);

    void add(Messages messages);
}
