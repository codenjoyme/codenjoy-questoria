package apofig.javaquest.field;


import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.Something;

import java.util.List;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 20:42
 */
public interface FieldLocator {

    Something getAt(Point point, Me founder);

    boolean isNear(Me founder, Something object);

    List<Something> getNear(Me founder);
}
