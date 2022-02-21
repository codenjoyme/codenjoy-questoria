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

import com.codenjoy.dojo.questoria.model.PlayerInfoImpl;
import com.codenjoy.dojo.questoria.model.items.*;

public class Gold extends TalkingObject implements Something, SetWorld, SetParameters<Integer>, Leaveable, Usable {

    private World world;
    private int amount;

    @Override
    public void answer(String message) {
        messenger.sayOnce("Ты не можешь делать это со мной!");
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Привет, я - " + amount + "$");
    }

    @Override
    public Something leaveAfter() {
        return world.make(' ');
    }

    @Override
    public char symbol() {
        return '$';
    }

    @Override
    public void getBy(PlayerInfoImpl player) {
        player.addGold(amount);
        leaveAfter();
        messenger.say("Ты подобрал меня! Спасибо!!");
    }

    @Override
    public void tryToLeave(Me hero) {
        messenger.sayOnce("Ну и ладно! Достанусь кому-то другому!!");
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void setParameters(Integer amount) {
        if (amount == null) {
            amount = 1; // TODO потестить этот кейз
        }
        this.amount = amount;
    }
}
