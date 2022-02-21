package com.codenjoy.dojo.questoria.model.items.monster.test;

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

import static org.junit.Assert.assertEquals;

public class TestSuiteTest {

    private TestSuite suite;

    @Before
    public void setUp() {
        suite = new TestSuite();
    }

    @Test
    public void testGetResults_containsFailed() {
        // given
        suite.add(new TestResult(new Integer[]{1, 2}, 2, 2));
        suite.add(new TestResult(new Integer[]{4, 5}, 3, 5));
        suite.add(new TestResult(new Integer[]{6, 3}, new IllegalArgumentException("Bla")));

        // when
        String results = suite.getResults();

        // then
        assertEquals("Для [1, 2] метод работает правильно - ты вернул “2”\n" +
                "Для [4, 5] метод должен вернуть “5”, но ты вернул “3”\n" +
                "Для [6, 3] метод сгенерировал Exception: java.lang.IllegalArgumentException: Bla",
                results);
    }

    @Test
    public void testGetResults_allValid() {
        // given
        suite.add(new TestResult(new Integer[]{1, 2}, 2, 2));
        suite.add(new TestResult(new Integer[]{4, 5}, 3, 3));
        suite.add(new TestResult(new Integer[]{6, 3}, 4, 4));

        // when
        String results = suite.getResults();

        // then
        assertEquals("OK", results);
    }

    @Test
    public void testGetResults_clear() {
        // given
        suite.add(new TestResult(new Integer[]{1, 2}, new IllegalArgumentException("Bla")));

        // when
        suite.clear();

        // then
        assertEquals("OK", suite.getResults());
    }

    @Test
    public void testIsEnough_allValid() {
        // given
        suite.add(new TestResult(new Integer[]{1, 2}, 2, 2));
        suite.add(new TestResult(new Integer[]{4, 5}, 3, 3));
        suite.add(new TestResult(new Integer[]{6, 3}, 4, 4));

        // when then
        assertEquals(false, suite.isEnough());
    }

    @Test
    public void testIsEnough_collect6Failed() {
        // given
        suite.add(new TestResult(new Integer[]{1, 2}, 2, 2)); // OK
        suite.add(new TestResult(new Integer[]{4, 5}, 3, 0)); // fail 1
        suite.add(new TestResult(new Integer[]{4, 3}, 4, 4)); // OK
        suite.add(new TestResult(new Integer[]{5, 4}, 4, 0)); // fail 2
        suite.add(new TestResult(new Integer[]{3, 5}, 4, 0)); // fail 3
        suite.add(new TestResult(new Integer[]{6, 6}, 4, 0)); // fail 4
        suite.add(new TestResult(new Integer[]{5, 7}, 7, 7)); // OK
        suite.add(new TestResult(new Integer[]{7, 3}, 4, 0)); // fail 5
        suite.add(new TestResult(new Integer[]{9, 3}, 8, 8)); // OK

        // then
        assertEquals(false, suite.isEnough());

        // when
        suite.add(new TestResult(new Integer[]{5, 5}, 4, 0)); // fail 6

        // then
        assertEquals(true, suite.isEnough());

        // when
        suite.add(new TestResult(new Integer[]{3, 2}, 9, 9)); // OK
        suite.add(new TestResult(new Integer[]{2, 2}, 2, 2)); // OK
        suite.add(new TestResult(new Integer[]{5, 4}, 4, 0)); // fail 7
        suite.add(new TestResult(new Integer[]{3, 5}, 4, 0)); // fail 8

        // then
        assertEquals(true, suite.isEnough());
    }

    @Test
    public void testIsEnough_afterException() {
        // given
        suite.add(new TestResult(new Integer[]{1, 2}, 2, 2)); // OK
        suite.add(new TestResult(new Integer[]{4, 3}, 4, 4)); // OK
        suite.add(new TestResult(new Integer[]{5, 7}, 7, 7)); // OK
        suite.add(new TestResult(new Integer[]{9, 3}, 8, 8)); // OK

        // then
        assertEquals(false, suite.isEnough());

        // when
        suite.add(new TestResult(new Integer[]{9, 3},
                new RuntimeException())); // error

        // then
        assertEquals(true, suite.isEnough());
    }

}
