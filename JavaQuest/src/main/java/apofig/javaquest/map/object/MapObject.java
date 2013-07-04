package apofig.javaquest.map.object;

import apofig.javaquest.map.Point;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:52 AM
 */
public abstract class MapObject implements ObjectSettings {

    protected Place place;
    private ObjectFactory factory;

    @Override
    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    public Something make(char c) {
        return factory.make(c, place);
    }

    public boolean isAt(Point point) {
        return place.getX() == point.getX() && place.getY() == point.getY();
    }

}
