package com.codenjoy.dojo.questoria.model;


import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.questoria.model.items.Something;

import java.util.List;

public interface FieldLocator {

    Something getAt(Point point, Me founder);

    boolean isNear(Me founder, Something object);

    List<Something> getNear(Me founder);
}
