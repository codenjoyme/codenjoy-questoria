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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SimpleIterator<T> implements Iterator<T> {

    private int index;
    private List<T> data;

    public SimpleIterator() {}

    public SimpleIterator(List<T> data) {
        reset(data);
    }

    public void reset() {
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < data.size();
    }

    @Override
    public T next() {
        return data.get(index++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void reset(List<T> data) {
        this.data = new LinkedList<>(data);
        reset();
    }
}