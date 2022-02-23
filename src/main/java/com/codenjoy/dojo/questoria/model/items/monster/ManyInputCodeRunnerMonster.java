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

import com.codenjoy.dojo.questoria.model.items.monster.test.TestResult;

public abstract class ManyInputCodeRunnerMonster extends WSCodeRunnerMonster {

    public ManyInputCodeRunnerMonster(String question, String signature, int weight) {
        super(question, signature, weight);
    }

    @Override
    public TestResult test(Object[] test, MethodRunner method) {
        try {
            Object actual = method.run(test);
            String expected = method(test);
            return new TestResult(test, actual, expected);
        } catch (Exception e) {
            return new TestResult(test, e);
        }
    }

    protected abstract String method(Object...inputs);

}