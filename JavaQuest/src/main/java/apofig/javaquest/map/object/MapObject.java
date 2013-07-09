package apofig.javaquest.map.object;

import apofig.javaquest.map.MapPlace;
import apofig.javaquest.map.Point;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:52 AM
 */
public abstract class MapObject implements ObjectSettings {

    private Place place;
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
        place.update(c);
        return factory.get(place);
    }

    public void move(int x, int y) {
        char ch = place.getChar();
        place.update(' ');
        place = new MapPlace(place, x, y);
        place.update(ch);
    }

    public boolean isAt(Point point) {
        return place.getX() == point.getX() && place.getY() == point.getY();
    }

    @Override
    public String toString() {
        return String.format("[object %s at %s]", this.getClass().getSimpleName(), place);
    }

}
