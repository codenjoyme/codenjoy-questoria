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

import java.util.Arrays;

public class TestResult {

    private String message;
    private boolean success;
    private boolean error;

    public TestResult(Object[] test, Object actual, Object expected) {
        success = actual.equals(expected) || actual.toString().equals(expected.toString());
        if (success) {
            message = String.format("Для %s метод работает правильно - ты вернул “%s”",
                    Arrays.toString(test), actual);
        } else {
            message = String.format("Для %s метод должен вернуть “%s”, но ты вернул “%s”",
                    Arrays.toString(test), expected, actual);
        }
        error = false;
    }

    public TestResult(Object[] test, Exception exception) {
        message = String.format("Для %s метод сгенерировал Exception: %s",
                Arrays.toString(test), processException(exception));
        success = false;
        error = true;
    }

    public TestResult(Exception exception) {
        message = String.format("Exception: %s",
                processException(exception));
        success = false;
        error = true;
    }

    protected String processException(Exception exception) {
        if (exception.getClass().equals(NullPointerException.class)) {
            return "Извини, NPE!";
        }
        return exception.toString().replaceAll("Dynamic[0-9]+", "Dynamic"); // TODO hotfix
    }

    public String message() {
        return message;
    }

    public boolean success() {
        return success;
    }

    public boolean error() {
        return error;
    }

    public boolean fail() {
        return !success;
    }

    @Override
    public String toString() {
        return String.format("[Test: success=%s,error=%s,fail=%s,message=%s]",
                success(), error(), fail(), message());
    }
}
