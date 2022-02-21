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
import com.codenjoy.dojo.questoria.model.PlayerInfoImpl;
import com.codenjoy.dojo.questoria.model.items.Something;
import com.codenjoy.dojo.questoria.model.items.TalkingObject;
import com.codenjoy.dojo.questoria.model.items.Usable;
import com.codenjoy.dojo.questoria.model.items.monster.CodeHelper;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.state.State;

public class Nothing extends TalkingObject implements Something, CodeHelper, Usable, State<Element, Player> {

    public Nothing() {
        super();
    }

    public Nothing(Point pt) {
        super(pt);
    }

    @Override
    public void answer(String message) {
        messenger.say("Ну и?..");
    }

    @Override
    public void ask() {
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return state(null).ch();
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public void getBy(PlayerInfoImpl info) {
        // do nothing
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.NOTHING;
    }
}
