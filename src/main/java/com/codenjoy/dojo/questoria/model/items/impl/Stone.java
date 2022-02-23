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

import java.util.Arrays;

public class Stone extends TalkingObject implements Something, Leaveable {

    public static final String MESSAGE_1 = "Ты попал в сказочный мир Ascii кодов. Тут каждый объект живой. \n" +
            "С каждым из них ты можешь взаимодействовать с помощью кода. \n" +
            "Этот мир не безопасен - тебе будут встречаться различные его обитатели. \n" +
            "Некоторые из них хотят тебе навредить, другие - помочь, \n" +
            "третьи - делают вид, что помогают. Будь внимателен (дальше...)";

    public static final String MESSAGE_2 = "Неподалеку, на юго западе есть золотое поле. Сходи, разведай. \n" +
            "Золото тебе пригодится в дальнейшем. По дороге будут встречаться монстры. \n" +
            "Чтобы избавиться от них - достаточно решить предложенную головоломку.";

    public static final String MESSAGE_INTRO = "На камне что-то написано (нажми say чтобы посмотреть...)";

    public static final String MESSAGE_GOODBYE = "Успехов, Странник!";

    private SimpleIterator<String> iterator = new SimpleIterator<String>(Arrays.asList(MESSAGE_1, MESSAGE_2));

    public Stone() {
        super();
    }

    public Stone(Point pt) {
        super(pt);
    }

    @Override
    public void answer(String message) {
        if (iterator.hasNext()) {
            messenger.sayOnce(iterator.next());
        }
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        iterator.reset();
        messenger.sayOnce(MESSAGE_INTRO);
    }

    @Override
    public void tryToLeave(Me hero) {
        iterator.reset();
        messenger.sayOnce(MESSAGE_GOODBYE);
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.STONE;
    }
}
