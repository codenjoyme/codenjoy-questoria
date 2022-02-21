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

import com.codenjoy.dojo.questoria.model.Messages;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestSuite {

    private List<TestResult> tests = new LinkedList<>();

    public String getResults() {
        if (isGreenLine()) {
            return "OK";
        }

        return collectTestMessages();
    }

    private String collectTestMessages() {
        List<String> messages =
                tests.stream()
                        .map(test -> test.message())
                        .collect(toList());
        return Messages.join(messages, "\n");
    }

    public boolean isGreenLine() {
        return !tests.stream()
                .filter(TestResult::fail)
                .findAny()
                .isPresent();
    }

    private boolean isErrorPresent() {
        return tests.stream()
                .filter(TestResult::error)
                .findAny()
                .isPresent();
    }

    public void add(TestResult testResult) {
        tests.add(testResult);
    }

    public boolean isEnough() {
        if (isGreenLine()) {
            return false;
        }

        if (isErrorPresent()) {
            return true;
        }
        if (countFailed() >= 6) {
            return true;
        }
        return false;
    }

    private long countFailed() {
        return tests.stream()
                .filter(TestResult::fail)
                .count();
    }

    public void clear() {
        tests.clear();
    }
}
