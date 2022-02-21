package com.codenjoy.dojo.questoria.model.items;

import com.codenjoy.dojo.questoria.model.Locator;
import com.codenjoy.dojo.services.Tickable;

public interface ObjectFactory extends Tickable {

    Something get(Place place, Me founder, Object params);

    void add(Me me);

    void remove(Me me);

    Locator getLocator();
}
