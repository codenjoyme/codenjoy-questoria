package com.codenjoy.dojo.questoria.model;

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

public class PlayerInfoImpl implements PlayerInfo {

    private int gold;
    private String name;
    private String portalCode;

    private PlayerInfoImpl() {}

    public PlayerInfoImpl(String name) {
        this.name = name;
    }

    public String toString() {
        String portalPart = String.format(" Портал: %s", portalCode);

        return String.format("Уровень:%s Опыт:%s Здоровье:%s Золото:%s%s",
                0, 0, 100, gold, (portalCode == null) ? "" : portalPart);
    }

    public void addGold(int count) {
        gold += count;
    }

    public int filchGold(int count) {
        if (gold < count) {
            int old = gold;
            gold = 0;
            return old;
        } else {
            gold -= count;
            return count;
        }
    }

    public String getName() {
        return name;
    }

    public void setPortalCode(String portal) {
        this.portalCode = portal;
    }

    public String getPortalCode() {
        return portalCode;
    }
}
