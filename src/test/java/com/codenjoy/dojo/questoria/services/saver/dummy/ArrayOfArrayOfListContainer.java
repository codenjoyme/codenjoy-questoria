package com.codenjoy.dojo.questoria.services.saver.dummy;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArrayOfArrayOfListContainer {

    private List<String>[][] array;

    private ArrayOfArrayOfListContainer() {}

    public ArrayOfArrayOfListContainer(int i) {
        array = new List[][] {
                new List[]{
                        new ArrayList(Arrays.asList("a1")),
                        new LinkedList(Arrays.asList("b1", "b2")),
                        new ArrayList(Arrays.asList("c1", "c2", "c3"))
                },
                new List[]{
                        new LinkedList(Arrays.asList("q1")),
                        new ArrayList(Arrays.asList("w1", "w2")),
                        new LinkedList(Arrays.asList("e1", "e2", "e3"))
                }
        };
    }
}
