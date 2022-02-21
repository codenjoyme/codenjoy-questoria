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
import com.codenjoy.dojo.questoria.model.items.Leaveable;
import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.questoria.model.items.Something;
import com.codenjoy.dojo.questoria.model.items.TalkingObject;
import com.codenjoy.dojo.questoria.model.items.monster.SimpleIterator;
import com.codenjoy.dojo.services.Point;

import java.util.LinkedList;
import java.util.List;

public class StoneForum extends TalkingObject implements Something, Leaveable {

    public static final String MESSAGE_INTRO = "На камне кто-то что-то написал (нажми say чтобы посмотреть...)";

    public static final String MESSAGE_LAST = "Ты тоже можешь что-то написать тут...";

    private boolean canWrite = false;
    private List<String> messages = new LinkedList<String>();
    private SimpleIterator<String> iterator = new SimpleIterator<String>(messages);

    public StoneForum() {
        super();
    }

    public StoneForum(Point pt) {
        super(pt);
    }

    @Override
    public void answer(String message) {
        if (canWrite) {
            messages.add(message);
            canWrite = false;
            return;
        }
        if (iterator.hasNext()) {
            messenger.sayOnce(iterator.next());
        } else {
            messenger.sayOnce(MESSAGE_LAST);
            canWrite = true;
        }
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        iterator.reset();
        canWrite = false;
        messenger.sayOnce(MESSAGE_INTRO);
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public void tryToLeave(Me hero) {
        iterator.reset(messages);
        canWrite = false;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.STONE_FORUM;
    }
}
