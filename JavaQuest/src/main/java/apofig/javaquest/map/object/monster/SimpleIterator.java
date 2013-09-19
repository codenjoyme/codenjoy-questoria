package apofig.javaquest.map.object.monster;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: sanja
 * Date: 19.09.13
 * Time: 14:08
 */
public class SimpleIterator<T> implements Iterator<T> {

    private int index;
    private List<T> data;

    public SimpleIterator() {}

    public SimpleIterator(List<T> data) {
        this.data = new LinkedList<>(data);
        reset();
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
}
