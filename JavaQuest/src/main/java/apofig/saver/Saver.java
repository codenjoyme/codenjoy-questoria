package apofig.saver;

import org.fest.reflect.core.Reflection;

import java.lang.reflect.Field;
import java.util.*;

/**
 * User: sanja
 * Date: 09.09.13
 * Time: 2:40
 */
public class Saver {
    List<Entry> data = new LinkedList<>();
    List<Integer> ids = new LinkedList<>();

    public String save(Object object) {
        process(object);

        for (Entry entry : data) {
            ids.add(System.identityHashCode(entry.getKey().object));
        }

        String result = "";
        for (Entry entry : data) {
            result += string(entry.getKey().object);
            result += " = {\n";
            for (Fld fld : entry.getValue()) {
                result += getString(fld);
            }
            result += "}\n";
        }

        return result;
    }

    private String getString(Fld fld) {
        if ("Map.Entry".equals(fld.name)) {
            Map.Entry entry = (Map.Entry)fld.value;
            return "    [" + getValue(entry.getKey()) + "] = " + getValue(entry.getValue()) + "\n";
        }
        return "    " + fld.name + " = " + getValue(fld.value) + "\n";
    }

    private String getValue(Object o) {
        if (o == null) {
            return "null";
        }

        if (dataContainsKey(o)) {
           return string(o);
        }

        if (Collection.class.isAssignableFrom(o.getClass())) {
            String result = "";
            result += "        [\n";
            for (Object a : (List)o) {
                result += getValue(a);
            }
            result += "        ]\n";
            return result;
        }

        if (Map.Entry.class.isAssignableFrom(o.getClass())) {
            return o.toString();
        }

        return o.toString();
    }

    private boolean dataContainsKey(Object object) {
        Key key = new Key(object);
        for (Entry entry : data) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    private String string(Object object) {
        return object.getClass().getSimpleName() + "@" + ids.indexOf(System.identityHashCode(object));
    }

    static class Key {
        private Object object;
        public Key(Object object) {
            this.object = object;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Key)) {
                throw new IllegalStateException("");
            }

            Key k = (Key)o;

            return k.object == this.object;
        }

        @Override
        public int hashCode() {
            if (object == null) {
                return 0;
            }
            return object.hashCode();
        }

    }

    static class Fld {
        private String name;
        private Object value;

        public Fld(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    static class Entry {
        Key key;
        List<Fld> value;

        public Entry(Object object, List<Fld> value) {
            this.key = new Key(object);
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public List<Fld> getValue() {
            return value;
        }
    }

    private void process(Object object) {
        if (object == null) return;
        if (dataContainsKey(object)) return;

        boolean isArray =
                object instanceof Object[] ||
                object instanceof int[] ||
                object instanceof char[] ||
                object instanceof short[] ||
                object instanceof float[] ||
                object instanceof long[] ||
                object instanceof byte[] ||
                object instanceof double[] ||
                object instanceof boolean[];
        if (object.getClass().getPackage() == null && !isArray) return;

        boolean isMap = Map.class.isAssignableFrom(object.getClass());
        boolean isList = List.class.isAssignableFrom(object.getClass());
        if (!isArray && !object.getClass().getPackage().getName().contains("apofig") && !isMap && !isList) {
            return;
        }

        if (isMap) {
            Set<Map.Entry<Object, Object>> entries = ((Map<Object, Object>) object).entrySet();
            List<Fld> list = new LinkedList<>();
            for (Map.Entry<?, ?> entry : entries) {
                list.add(new Fld("Map.Entry", entry));
            }
            Collections.sort(list, getFldComparator());
            data.add(new Entry(object, list));

            for (Map.Entry<?, ?> entry : entries) {
                process(entry.getKey());
                process(entry.getValue());
            }
            return;
        }

        if (isArray) {
            LinkedList<Fld> list = new LinkedList<>();
        if (object instanceof  int[]) {    // TODO как я не люблю массивы в джаве
                int[] array = (int[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof  boolean[]) {
                boolean[] array = (boolean[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof  byte[]) {
                byte[] array = (byte[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof  long[]) {
                long[] array = (long[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof  char[]) {
                char[] array = (char[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof  double[]) {
                double[] array = (double[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof  short[]) {
                short[] array = (short[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof float[]) {
                float[] array = (float[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }
            if (object instanceof Object[]) {
                Object[] array = (Object[])object;
                for (int index = 0; index < array.length; index++) {
                    list.add(new Fld("[" + index + "]", array[index]));
                }
            }

            data.add(new Entry(object, list));

            for (Fld fld : list) {
                process(fld.value);
            }
            return;
        }

        if (isList) {
            LinkedList<Fld> list = new LinkedList<>();
            List container = (List) object;
            for (int index = 0; index < container.size(); index++) {
                list.add(new Fld("[" + index + "]", container.get(index)));
            }
            data.add(new Entry(object, list));

            for (Object o : container) {
                process(o);
            }
            return;
        }

        for (Field field : getFields(object)) {
            if (field.getName().contains("this$")) continue;
            Object o = Reflection.field(field.getName()).ofType(field.getType()).in(object).get();
            if (!dataContainsKey(object)) {
                data.add(new Entry(object, new LinkedList<Fld>()));
            }
            dataGet(object).add(new Fld(field.getName(), o));
        }

        for (Entry entry : data.toArray(new Entry[0])) {
            for (Fld fld : entry.getValue()) {
                Object o = fld.value;
                if (!dataContainsKey(o)) {
                    process(o);
                }
            }
        }
    }

    private List<Fld> dataGet(Object object) {
        Key key = new Key(object);
        for (Entry entry : data) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("не найдено!");
    }

    private Field[] getFields(Object object) {
        Field[] result = object.getClass().getDeclaredFields();
        Arrays.sort(result, getFieldComparator());
        return result;
    }

    private Comparator<Field> getFieldComparator() {
        return new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return (o1.getType() + o1.getName()).compareTo(o2.getType() + o2.getName());
            }
        };
    }


    private Comparator<Fld> getFldComparator() {
        return new Comparator<Fld>() {
            @Override
            public int compare(Fld o1, Fld o2) {
                return (o1.value.getClass().getSimpleName() + o1.name).compareTo(
                        o2.value.getClass().getSimpleName() + o2.name);
            }
        };
    }
}
