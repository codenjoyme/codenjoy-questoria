package com.codenjoy.dojo.questoria.model.items.impl.dron;

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

import com.codenjoy.dojo.questoria.model.items.*;

public class DronMentor extends TalkingObject implements Something, SetWorld, MeetWithHero {

    public static final String MESSAGE =
            "Видишь все это золото? Оно твое. Ты можешь собрать его намного быстрее, \n" +
            "если воспользуешься помощником. Я научу тебя его делать. \n" +
            "Чтобы робот заработал, тебе нужно наделить его разумом. \n" +
            "    public String whereToGo(String nearMe) {\n" +
            "        return \"command\";\n" +
            "    }\n" +
            "Реализуй этот метод и посмотрим что у тебя получится. \n" +
            "Строка nearMe - это то что видит робо вокруг себя. Расшифруй ее так\n" +
            "      012\n" +
            "      7*3\n" +
            "      654\n" +
            "Команды управления робота - left, right, up, down.\n" +
            "Если робот убежит, тебе прийдется его искать. \n" +
            "Только находясь рядом с ним ты можешь его модифицировать. \n" +
            "При виде тебя робот останавливается и ждет твоей команды. \n" +
            "Нажми say и твой робот появится...";

    private Me hero;
    private World world;

    @Override
    public void answer(String message) {
        Dron dron = leaveAfter();
        dron.ask();
    }

    @Override
    public void ask() {
        messenger.sayOnce(MESSAGE);
    }

    @Override
    public Dron leaveAfter() {
        Dron dron = (Dron)world.make(Dron.CHAR);
        dron.setHero(hero);
        return dron;
    }

    @Override
    public void meetWith(Me hero) {
        this.hero = hero;
    }

    @Override
    public void leave(Me hero) {
        // do nothing
    }

    @Override
    public char symbol() {
        return 'M';
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
