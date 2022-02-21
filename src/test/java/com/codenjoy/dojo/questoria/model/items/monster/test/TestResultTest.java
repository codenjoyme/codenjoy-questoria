package com.codenjoy.dojo.questoria.model.items.monster.test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestResultTest {

    @Test
    public void testException_withoutTestData() {
        // given
        TestResult result = new TestResult(new IllegalArgumentException("Bla bla"));

        // when then
        assertEquals("[Test: success=false,error=true,fail=true," +
                        "message=Exception: java.lang.IllegalArgumentException: Bla bla]",
                result.toString());
    }

    @Test
    public void testException_withTestData() {
        // given
        TestResult result = new TestResult(new Integer[]{1, 2},
                new IllegalArgumentException("Bla bla"));

        // when then
        assertEquals("[Test: success=false,error=true,fail=true," +
                        "message=Для [1, 2] метод сгенерировал Exception: java.lang.IllegalArgumentException: Bla bla]",
                result.toString());
    }

    @Test
    public void testFail() {
        // given
        TestResult result = new TestResult(new Integer[]{1, 2}, 2, 4);

        // when then
        assertEquals("[Test: success=false,error=false,fail=true," +
                        "message=Для [1, 2] метод должен вернуть “4”, но ты вернул “2”]",
                result.toString());
    }

    @Test
    public void testSuccess() {
        // given
        int same = 4;
        TestResult result = new TestResult(new Integer[]{1, 2}, same, same);

        // when then
        assertEquals("[Test: success=true,error=false,fail=false," +
                        "message=Для [1, 2] метод работает правильно - ты вернул “4”]",
                result.toString());
    }


}