package com.codenjoy.dojo.questoria.model.items.monster.test;

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