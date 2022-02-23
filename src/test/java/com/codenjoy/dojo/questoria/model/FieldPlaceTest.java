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

import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.questoria.client.Element.NOTHING;
import static com.codenjoy.dojo.services.PointImpl.pt;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FieldPlaceTest {

    private FieldPlace place;

    @Before
    public void setup() {
        FieldOld field = new FieldOld(10, NOTHING.ch());
        field.set(3, 3, '1');  // 123
        field.set(4, 3, '2');  // 456
        field.set(5, 3, '3');  // 789
        field.set(3, 4, '4');
        field.set(4, 4, '5');
        field.set(5, 4, '6');
        field.set(3, 5, '7');
        field.set(4, 5, '8');
        field.set(5, 5, '9');
        place = new FieldPlace(field, 4, 4);
    }

    @Test
    public void shouldWorkNear() {
        assertEquals(
                "[field[4,3]='2', field[5,4]='6', field[4,5]='8', field[3,4]='4']", place.near().toString());
    }

    @Test
    public void shouldWorkNearD() {
        assertEquals("field[5,5]='9'", place.near(1, 1).toString());
        assertEquals("field[3,3]='1'", place.near(-1, -1).toString());
    }


    @Test
    public void shouldWorkMove() {
        place.move(place.getX(), place.getY() + 1);
        assertTrue(place.itsMe(pt(4, 5)));

        assertEquals('8', place.getChar());

        place.move(place.getX() + 1, place.getY());
        assertTrue(place.itsMe(pt(5, 5)));

        assertEquals('9', place.getChar());
    }

    @Test
    public void shouldToString() {
        assertEquals("field[4,4]='5'", place.toString());
    }

    @Test
    public void shouldWorkUpdate() {
        assertEquals('5', place.getChar());
        place.update('A');
        assertEquals('A', place.getChar());
    }

}
