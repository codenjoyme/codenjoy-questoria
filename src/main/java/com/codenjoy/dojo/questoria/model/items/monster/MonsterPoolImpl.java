package com.codenjoy.dojo.questoria.model.items.monster;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.questoria.model.Action;
import com.codenjoy.dojo.questoria.model.items.Something;
import com.google.common.collect.Lists;

import java.util.List;

public class MonsterPoolImpl implements MonsterPool {

    private List<Monster> monsters;
    private Monster monster;
    private int count;

    private MonsterPoolImpl() {}

    public MonsterPoolImpl(Iterable<Monster> otherMonsters) {
        monsters = Lists.newLinkedList(otherMonsters);
        count = monsters.size() + 1;
    }

    public Monster next() {
        if (monster != null) {
            return monster;
        }

        if (!monsters.isEmpty()) {
            monster = monsters.remove(0);
        } else {
            monster = new Monster("Я монстр №" + count + "! Борись со мной!",
                    "die!",
                    "Я убью тебя!",
                    CodeRunnerMonster.LEAVE,
                    "", count);
            count++;
        }
        monster.onKill(new OnKill());
        return monster;
    }

    class OnKill implements Action {

        private OnKill() {}

        @Override
        public void act(Something object) {
            monster = null;
        }
    }
}
