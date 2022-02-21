package com.codenjoy.dojo.questoria.model;

import com.codenjoy.dojo.questoria.model.items.monster.MonsterFactory;

public interface Settings {

    int viewSize();

    FieldLoader fieldLoader();

    MonsterFactory monsters();
}
