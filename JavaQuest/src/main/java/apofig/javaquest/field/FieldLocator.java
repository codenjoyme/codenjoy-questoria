package apofig.javaquest.field;


import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.Something;

import java.util.List;

public interface FieldLocator {

    Something getAt(Point point, Me founder);

    boolean isNear(Me founder, Something object);

    List<Something> getNear(Me founder);
}
