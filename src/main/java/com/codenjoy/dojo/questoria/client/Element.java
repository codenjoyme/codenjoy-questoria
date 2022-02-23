package com.codenjoy.dojo.questoria.client;

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


import com.codenjoy.dojo.services.printer.CharElement;

public enum Element implements CharElement {

        // Empty place where the hero can go.

    NOTHING(' '),

        // Fog of war.

    FOG('?'),

        // Wall you can't walk through.

    WALL('#'),

        // Your hero.

    HERO('I'),

        // Your hero died. His body will disappear in the next tick.

    HERO_DEAD('i'),

        // Heroes of other players.

    OTHER_HERO('A'),

        // Another player's hero died.

    OTHER_HERO_DEAD('a'),

        // Heroes of other players in other team.

    ENEMY_HERO('E'),

        // Player's Hero from the other team who died.

    ENEMY_HERO_DEAD('e'),

        // Gold. It must be picked up.

    GOLD('$'),

        // Stone. Contains information about game.

    STONE('O'),

        // Stone forum. This is something like forum.

    STONE_FORUM('0'),

        // Drone. You can program the drone to collect gold.

    DRONE('*'),

        // Drone mentor. Helps you understand how a drone works.

    DRONE_MENTOR('M'),

        // Monster. You have to fight the monster by solving
        // this or that puzzle. If you give up, the monster
        // will take the gold. If you win, you will receive
        // a prize from the monster.

    MONSTER('@');

    private final char ch;

    Element(char ch) {
        this.ch = ch;
    }

    @Override
    public char ch() {
        return ch;
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }
}
