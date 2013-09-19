package apofig.javaquest.map.object;

import apofig.javaquest.map.Point;

/**
 * User: sanja
 * Date: 05.09.13
 * Time: 23:18
 */
public interface World {
    Place place();

    void move(int x, int y);

    Something make(char c);

    boolean itsOpenedBy(Me me);
}
