package apofig.javaquest.map;

import java.util.Iterator;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:05 PM
 */
public interface MapLoader {
    Map map();

    Point initPosition();

    int height();
    int width();
}
