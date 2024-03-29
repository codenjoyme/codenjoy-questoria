package com.codenjoy.dojo.questoria.services;

/*-
 * #%L
 * expansion - it's a dojo-like platform from developers to developers.
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


import com.codenjoy.dojo.questoria.model.items.monster.MonsterFactory;
import com.codenjoy.dojo.questoria.model.items.monster.MonsterLoader;
import com.codenjoy.dojo.questoria.model.items.monster.MonsterPool;
import com.codenjoy.dojo.questoria.model.items.monster.MonsterPoolImpl;
import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.settings.AllSettings;
import com.codenjoy.dojo.services.settings.PropertiesKey;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.questoria.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements AllSettings<GameSettings> {

    public enum Keys implements PropertiesKey {

        VIEW_SIZE,
        PICK_GOLD_SCORE,
        KILL_MONSTER_SCORE,
        SCORE_COUNTING_TYPE;

        private String key;

        Keys() {
            key = key(GameRunner.GAME_NAME);
        }

        @Override
        public String key() {
            return key;
        }
    }

    @Override
    public List<Key> allKeys() {
        return Arrays.asList(Keys.values());
    }

    public GameSettings() {
        initAll();

        integer(VIEW_SIZE, 41);
        integer(PICK_GOLD_SCORE, 10);
        integer(KILL_MONSTER_SCORE, 100);

        Levels.setup(this);
    }

    public int viewSize() {
        return integer(VIEW_SIZE);
    }

    public MonsterFactory monsters() {
        // нельзя анонимно, потому что loader/saver захотят изучить Settings внутренности
        return new MonsterFactoryImpl();
    }

    public static class MonsterFactoryImpl implements MonsterFactory {
        @Override
        public MonsterPool newMonsters() {
            return new MonsterPoolImpl(new MonsterLoader());
        }
    }

    public Calculator<Void> calculator() {
        return new Calculator<>(new Scores(this));
    }
}