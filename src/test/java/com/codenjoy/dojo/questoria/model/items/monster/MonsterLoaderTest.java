package com.codenjoy.dojo.questoria.model.items.monster;

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

import com.google.common.collect.Lists;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MonsterLoaderTest {

    @Test
    public void test() {
        assertEquals("[[Monster: FizzBuzzMonster with complexity: 1], " +
                "[Monster: PrimeFactoryMonster with complexity: 2], " +
                "[Monster: FibonacciNumbersMonster with complexity: 3], " +
                "[Monster: SumSquareDifferenceMonster with complexity: 4], " +
                "[Monster: XthPrimeMonster with complexity: 5], " +
                "[Monster: PowerDigitSumMonster with complexity: 6], " +
                "[Monster: FactorialMonster with complexity: 7], " +
                "[Monster: LongDivisionMonster with complexity: 8], " +
                "[Monster: MakeBricksMonster with complexity: 9]]",
                Lists.newArrayList(new MonsterLoader().iterator()).toString());
    }
}
