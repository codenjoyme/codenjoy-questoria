package apofig.javaquest.map;

import apofig.javaquest.map.object.Something;

import java.io.OutputStream;
import java.util.List;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:52
 */
public interface TerritoryMap {

    List<Something> getSomethingNearMe();

    Something getAt(int x, int y);

    int getY();

    int getX();

    boolean isNear(int xx, int yy, Something object);

    void changePos(int x, int y);

    String getViewArea();

}
