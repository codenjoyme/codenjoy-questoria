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

public class LongDivisionMonsterTest {

    @Test
    public void shouldWork() throws Exception {
        LongDivisionMonster monster = new LongDivisionMonster();
        assertEquals("0.5", monster.method(1, 2));
        assertEquals("1", monster.method(1, 1));
        assertEquals("1", monster.method(5, 5));
        assertEquals("11", monster.method(55, 5));
        assertEquals("1.25", monster.method(55, 44));
        assertEquals("0", monster.method(0, 56));
        assertEquals("56", monster.method(56, 1));
        assertEquals("-0.5", monster.method(1, -2));
        assertEquals("-0.5", monster.method(-1, 2));
        assertEquals("0.5", monster.method(-1, -2));
        assertEquals("0.001", monster.method(1, 1000));
        assertEquals("1.2(4)", monster.method(56, 45));
        assertEquals("1.00(90)", monster.method(111, 110));
        assertEquals("10.0(90)", monster.method(111, 11));
        assertEquals("1010.0(90)", monster.method(11111, 11));
        assertEquals("0.0(495)", monster.method(-11, -222));
        assertEquals("-5.0(45)", monster.method(111, -22));
        assertEquals("0.000(3)", monster.method(1, 3000));
        assertEquals("1.1(153846)", monster.method(87, 78));
        assertEquals("0.803(571428)", monster.method(45, 56));
        assertEquals("1.(593984962406015037)", monster.method(212, 133));
        assertEquals("96.6(1739130434782608695652)", monster.method(11111, 115));
        assertEquals("0.3(5652173913043478260869)", monster.method(123, 345));
        assertEquals("0.8576942320118070532237143486041032667124906968586017840186012266888836921194851616559161340739231484", monster.method(66666666, 77727777));
        assertEquals("8.5769423201180705322371434860410326671249069685860178401860122668888369211948516165591613407392314847", monster.method(666666660, 77727777));
        assertEquals("85.7694309253951322673994120762759564567464112247141529983428059238030371899258141588263756955847311713", monster.method(666666660, 7772777));
        assertEquals("1.0(309278350515463917525773195876288659793814432989690721649484536082474226804123711340206185567010)", monster.method(100, 97));
        assertEquals("Div by zero error!", monster.method(999, 0));

//        LegacyApprovals.LockDown(this, "method", Range.get(-100, 100), Range.get(-100, 100));
        //TODO: option_1: after adding kata module as dependency delete all algorithms and related tests
        //TODO: option_2: fix the test otherwise
    }

    public String method(Integer i1, Integer i2) {
        return new LongDivisionMonster().method(i1, i2);
    }
}
