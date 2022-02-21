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

public class MyDron {

    public String whereToGo(String nearMe) {
        char atWay = ' ';
        int random = 0;
        do {
            random = new java.util.Random().nextInt(4);
            switch (random) {
                case 0:
                    atWay = nearMe.charAt(1);
                    break;
                case 1:
                    atWay = nearMe.charAt(5);
                    break;
                case 2:
                    atWay = nearMe.charAt(7);
                    break;
                case 3:
                    atWay = nearMe.charAt(3);
                    break;
                default:
                    atWay = nearMe.charAt(5);
                    break;
            }

        } while (atWay != ' ' && atWay != '$');

        switch (new java.util.Random().nextInt(4)) {
            case 0:
                return "up";
            case 1:
                return "down";
            case 2:
                return "left";
            case 3:
                return "right";
            default:
                return "down";
        }
    }

}
