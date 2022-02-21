package com.codenjoy.dojo.questoria.model.items.monster.impl;

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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PrimeFactoryMonsterTest {

    @Test
    public void shouldWork() {
        PrimeFactoryMonster monster = new PrimeFactoryMonster();
        assertEquals("[1]", monster.method(1));
        assertEquals("[2]", monster.method(2));
        assertEquals("[3]", monster.method(3));
        assertEquals("[2,2]", monster.method(4));
        assertEquals("[5]", monster.method(5));
        assertEquals("[2,3]", monster.method(6));
        assertEquals("[7]", monster.method(7));
        assertEquals("[2,2,2]", monster.method(8));
        assertEquals("[3,3]", monster.method(9));
        assertEquals("[2,5]", monster.method(10));
        assertEquals("[11]", monster.method(11));
        assertEquals("[2,2,3]", monster.method(12));
        assertEquals("[13]", monster.method(13));
        assertEquals("[2,7]", monster.method(14));
        assertEquals("[3,5]", monster.method(15));
        assertEquals("[2,2,2,2]", monster.method(16));
        assertEquals("[17]", monster.method(17));
        assertEquals("[2,3,3]", monster.method(18));
        assertEquals("[19]", monster.method(19));
        assertEquals("[2,2,5]", monster.method(20));
        assertEquals("[3,7]", monster.method(21));
        assertEquals("[2,11]", monster.method(22));
        assertEquals("[23]", monster.method(23));
        assertEquals("[2,2,2,3]", monster.method(24));
        assertEquals("[5,5]", monster.method(25));
        assertEquals("[2,13]", monster.method(26));
    }
}
