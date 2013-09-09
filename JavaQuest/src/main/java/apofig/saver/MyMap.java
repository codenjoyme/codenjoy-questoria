package apofig.saver;

import java.util.*;

/**
 * User: sanja
 * Date: 10.09.13
 * Time: 0:57
 */
public class MyMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private Set<Entry<K, V>> entries = new MySet<Entry<K, V>>();

    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries;
    }

    @Override
    public Object put(Object key, Object value) {
        entries.add(new MyMapEntry(key, value));
        return null;
    }

    class MySet<T> extends AbstractSet<T> implements Set<T> {

        private List<T> elements = new LinkedList();

        @Override
        public Iterator<T> iterator() {
            return elements.iterator();
        }

        @Override
        public int size() {
            return elements.size();
        }

        @Override
        public boolean add(T o) {
            return elements.add(o);
        }
    }

    class MyMapEntry implements Map.Entry {

        private Object key;
        private Object value;

        public MyMapEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            this.value = value;
            return value;
        }
    }
}
