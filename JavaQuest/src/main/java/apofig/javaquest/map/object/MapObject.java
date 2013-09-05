package apofig.javaquest.map.object;

import apofig.javaquest.map.MapPlace;
import apofig.javaquest.map.Point;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:52 AM
 */
public abstract class MapObject implements ObjectSettings {

    protected World world;

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    public Something make(char c) {
        return world.make(c);
    }

    public void move(int x, int y) {
        world.move(x, y);
    }

}
