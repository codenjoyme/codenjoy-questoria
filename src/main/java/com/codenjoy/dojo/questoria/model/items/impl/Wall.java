package com.codenjoy.dojo.questoria.model.items.impl;

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

import com.codenjoy.dojo.questoria.client.Element;
import com.codenjoy.dojo.questoria.model.Player;
import com.codenjoy.dojo.questoria.model.items.Something;
import com.codenjoy.dojo.questoria.model.items.TalkingObject;
import com.codenjoy.dojo.services.Point;

public class Wall extends TalkingObject implements Something {

    public Wall() {
        super();
    }

    public Wall(Point pt) {
        super(pt);
    }

    @Override
    public void answer(String message) {
        messenger.sayOnce("Ты не можешь это делать со мной!");
    }

    @Override
    public void ask() {
        messenger.say("Пожалуйста, остановись!");
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.WALL;
    }
}
