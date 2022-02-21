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

import com.codenjoy.dojo.questoria.model.items.monster.Assertions;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SumSquareDifferenceMonsterTest {

    @Test
    public void shouldWork() {
        int[] primes = new int[] {0, 0, 4, 22, 70, 170, 350, 644, 1092, 1740, 2640, 3850, 5434, 7462, 10010, 13160, 17000, 21624};
        SumSquareDifferenceMonster monster = new SumSquareDifferenceMonster();
        Assertions.assertMonster(primes, monster);
        assertEquals("25164150", monster.method(100));
        assertEquals("250166416500", monster.method(1000));
        assertEquals("2500166641665000", monster.method(10000));
    }
}
