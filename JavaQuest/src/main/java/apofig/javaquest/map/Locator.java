package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.ObjectFactory;
import apofig.javaquest.map.object.Place;
import apofig.javaquest.map.object.Something;

import java.util.LinkedList;
import java.util.List;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 21:46
 */
public class Locator implements MapLocator {

    private TerritoryMap map;
    private ObjectFactory factory;

    private Locator() {}

    public Locator(TerritoryMap map, ObjectFactory factory) {
        this.map = map;
        this.factory = factory;
    }

    @Override
    public List<Something> getNear(Me founder) {
        List<Place> near = map.getAt(new PointImpl(founder.getX(), founder.getY())).near();
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
        return get(founder, map.getAt(point));
    }

    @Override
    public boolean isNear(Me founder, Something object) {
        return getNear(founder).contains(object);
    }
}
