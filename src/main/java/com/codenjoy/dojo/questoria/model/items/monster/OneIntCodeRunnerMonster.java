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

public abstract class OneIntCodeRunnerMonster extends ManyInputCodeRunnerMonster {

    public static int COUNT_TESTS = 100;

    public static String SIGNATURE =
            "public String method(int number) {\n" +
            "    return |;\n" +
            "}";

    public OneIntCodeRunnerMonster(String question, int weight) {
        super(question, SIGNATURE, weight);
    }

    protected Object[][] getTestData() {
        Object[][] result = new Object[COUNT_TESTS][1];
        for (int index = 0; index < result.length; index ++) {
            result[index][0] = index + 1;
        }
        return result;
    }

    protected String method(Object... input) {
        return method((Integer)input[0]);
    }

    protected abstract String method(int input);

}
