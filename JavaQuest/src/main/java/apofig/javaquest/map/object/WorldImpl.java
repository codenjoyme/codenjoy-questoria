package apofig.javaquest.map.object;

import apofig.javaquest.map.MapPlace;
import apofig.javaquest.map.Point;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:52 AM
 */
public class WorldImpl implements World {

    private Place place;
    private String name;
    private ObjectFactory factory;

    public WorldImpl(ObjectFactory factory, Place place, Object owner) {
        this.factory = factory;
        this.place = place;
        this.name = owner.getClass().getSimpleName();
    }

    @Override
    public Something make(char c) {
        place.update(c);
        return factory.get(place);
    }

    @Override
    public void move(int x, int y) {
        char ch = place.getChar();
        place.update(' ');
        place = new MapPlace(place, x, y);
        place.update(ch);
    }

    @Override
    public boolean isAt(Point point) {
        return place.getX() == point.getX() && place.getY() == point.getY();
    }

    @Override
    public String toString() {
        return String.format("[object %s at %s]", name, place);
    }

    @Override
    public Place getPlace() {
        return place;
    }
}
