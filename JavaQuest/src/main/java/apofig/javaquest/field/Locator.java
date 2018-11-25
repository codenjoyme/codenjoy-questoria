package apofig.javaquest.field;

import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.ObjectFactory;
import apofig.javaquest.field.object.Place;
import apofig.javaquest.field.object.Something;

import java.util.LinkedList;
import java.util.List;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 21:46
 */
public class Locator implements FieldLocator {

    private TerritoryField field;
    private ObjectFactory factory;

    private Locator() {}

    public Locator(TerritoryField field, ObjectFactory factory) {
        this.field = field;
        this.factory = factory;
    }

    @Override
    public List<Something> getNear(Me founder) {
        List<Place> near = field.getAt(new PointImpl(founder.getX(), founder.getY())).near();
        List<Something> result = new LinkedList<Something>();
        for (Place place : near) {
            if (place.getChar() == ' ') continue;
            result.add(get(founder, place));
        }
        return result;
    }

    private Something get(Me founder, Place place) {
        // TODO продумать как локатор будет конфигурировать объекты, как это сейчас делает ObjectFactory
        return factory.get(place, founder, null);
    }

    @Override
    public Something getAt(Point point, Me founder) {
        return get(founder, field.getAt(point));
    }

    @Override
    public boolean isNear(Me founder, Something object) {
        return getNear(founder).contains(object);
    }
}
