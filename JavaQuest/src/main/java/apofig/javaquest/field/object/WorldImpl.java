package apofig.javaquest.field.object;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:52 AM
 */
public class WorldImpl implements World {

    private Place place;
    private String name;
    private ObjectFactory factory;
    private Me founder;

    private WorldImpl() {}

    public WorldImpl(ObjectFactory factory, Place place, Something object, Me founder) {
        this.factory = factory;
        this.place = place;
        this.founder = founder;
        this.name = object.getClass().getSimpleName();
    }

    @Override
    public Something make(char c, Object... params) {
        place.update(c);
        // TODO подумать над этим - некрасиво как-то
        Object parameter = (params != null && params.length > 0) ? params[0] : null;
        return factory.get(place, founder, parameter);
    }

    @Override
    public void move(int x, int y) {
        char ch = place.getChar();
        place.update(' ');
        place.move(x, y);
        place.update(ch);
    }

    @Override
    public String toString() {
        return String.format("[object %s at %s]", name, place);
    }

    @Override
    public Place place() {
        return place;
    }

}
