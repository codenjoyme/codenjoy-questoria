package com.codenjoy.dojo.questoria.model;

import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.questoria.model.items.monster.Monster;
import org.apache.commons.lang3.StringUtils;

public class Portal {

    private Me hero;
    private Monster monster;

    public Portal() {}

    public Portal(Me hero, Monster monster) {
        this.hero = hero;
        this.monster = monster;
    }

    public String getHash() {
        String code = monster.getClass().getName()
                + hero.getName()
                + "_soul%#$%&#%#%";
        int hash = code.hashCode();
        return StringUtils.leftPad(Integer.toHexString(hash).toUpperCase(), 8, '0');
    }

    public Monster getMonster() {
        return monster;
    }
}
