package com.codenjoy.dojo.questoria.services;

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

import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.services.settings.SettingsReader;

import static com.codenjoy.dojo.questoria.services.GameSettings.Keys.KILL_MONSTER_SCORE;
import static com.codenjoy.dojo.questoria.services.GameSettings.Keys.PICK_GOLD_SCORE;

public class Scores extends ScoresMap<Void> {

    public Scores(SettingsReader settings) {
        super(settings);

        put(Event.Type.PICK_GOLD,
                value -> settings.integer(PICK_GOLD_SCORE));

        put(Event.Type.KILL_MONSTER,
                value -> settings.integer(KILL_MONSTER_SCORE));
    }
}