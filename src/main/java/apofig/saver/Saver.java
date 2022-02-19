package apofig.saver;

import org.apache.commons.lang.ArrayUtils;
import org.fest.reflect.core.Reflection;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Saver {
    private List<Entry> data = new LinkedList<Entry>();
    private List<Integer> ids = new LinkedList<Integer>();
    private List<Class<?>> exclude = new LinkedList<Class<?>>();
    private List<Class<?>> excludeParent = new LinkedList<Class<?>>();
    private Object main;

    public Saver exclude(Class<?>... classes) {
        this.exclude.addAll(Arrays.asList(classes));
        return this;
    }

    public String save(Object object) {
        main = object;

        List<Object> objects = Arrays.asList(main);

        do {
            objects = parse(objects);
        } while (!objects.isEmpty());

        return buildString();
    }

    private String buildString() {
        for (Entry entry : data) {
            ids.add(System.identityHashCode(entry.getKey().object));
        }

        List<Map<String, Object>> objects = getDOM();

        return getJSON(objects);
    }

    private List<Map<String, Object>> getDOM() {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();

        for (Entry entry : data) {
            if (entry.value == null) continue;
            Object object = entry.getKey().object;

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("type", object.getClass().getName());
            map.put("id", string(object));
            map.put("fields", getFieldDOM(entry));
            if (object.getClass().isEnum()) {
                map.put("value", object.toString());
            }

            result.add(map);
        }
        return result;
    }

    private List<Object> getFieldDOM(Entry entry) {
        List<Object> result = new LinkedList<Object>();

        for (Object o : entry.getValue()) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (o instanceof Fld) {
                Fld fld = (Fld) o;

                map.put((String) getValue(fld.name), getValue(fld.value));

                result.add(map);
            } else {
                result.add(getValue(o));
            }
        }
        return result;
    }

    private String getJSON(List<Map<String, Object>> objs) {
        JSONObject json = new JSONObject();
        try {
            json.put("objects", objs);
            json.put("main", string(main));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return json.toString();
    }

    private Object getValue(Object o) {
        if (o == null) {
            return null;
        }

        if (dataContainsKey(o)) {
            return string(o);
        }

        if (Collection.class.isAssignableFrom(o.getClass())) {
            List<Object> result = new LinkedList<Object>();
            for (Object a : (List) o) {
                result.add(getValue(a));
            }
            return result;
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

    public Saver excludeChildren(Class<?>... classes) {
        this.excludeParent.addAll(Arrays.asList(classes));
        return this;
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

            Key k = (Key) o;

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
        private Object name;
        private Object value;

        public Fld(Object name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    static class Entry {
        Key key;
        List<?> value;

        public Entry(Object object, List<?> value) {
            this.key = new Key(object);
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public List<?> getValue() {
            return value;
        }
    }

    private List<Object> parse(List<Object> objects) {
        List<Object> toProcess = new LinkedList<Object>();
        for (Object object : objects) {

            if (object == null) continue;
            if (dataContainsKey(object)) continue;

            boolean isExclude = false;
            for (Class<?> clazz : excludeParent) {
                if (clazz.isAssignableFrom(object.getClass())) {
                    isExclude = true;
                }
            }
            isExclude |= exclude.contains(object.getClass());

            if (isExclude) {
                if (!dataContainsKey(object)) {
                    data.add(new Entry(object, null));
                }
                continue;
            }

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
            if (object.getClass().getPackage() == null && !isArray) continue;

            boolean isMap = Map.class.isAssignableFrom(object.getClass());
            boolean isList = List.class.isAssignableFrom(object.getClass());
            boolean isClass = Class.class.equals(object.getClass());
            if (!isArray && !isMap && !isList && !isClass) {
                boolean isApofig = object.getClass().getPackage().getName().contains("apofig");
                if (!isApofig) continue;
            }

            if (isMap) {
                Set<Map.Entry<Object, Object>> entries = ((Map<Object, Object>) object).entrySet();
                List<Map.Entry<Object, Object>> container = sort(entries);
                List<Fld> list = new LinkedList<Fld>();
                for (Map.Entry<?, ?> entry : container) {
                    list.add(new Fld(entry.getKey(), entry.getValue()));
                }
                data.add(new Entry(object, list));

                for (Map.Entry<?, ?> entry : container) {
                    toProcess.add(entry.getKey());
                    toProcess.add(entry.getValue());
                }
                continue;
            }

            if (isArray) {
                List<Object> list = null;
                if (object instanceof int[]) {    // TODO как я не люблю массивы в джаве
                    int[] array = (int[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof boolean[]) {
                    boolean[] array = (boolean[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof byte[]) {
                    byte[] array = (byte[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof long[]) {
                    long[] array = (long[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof char[]) {
                    char[] array = (char[]) object;
                    list = (List)Arrays.asList(String.valueOf(array));
                }
                if (object instanceof double[]) {
                    double[] array = (double[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof short[]) {
                    short[] array = (short[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof float[]) {
                    float[] array = (float[]) object;
                    list = (List)Arrays.asList(ArrayUtils.toObject(array));
                }
                if (object instanceof Object[]) {
                    Object[] array = (Object[]) object;
                    list = (List)Arrays.asList(array);
                }

                data.add(new Entry(object, list));

                for (Object o : list) {
                    toProcess.add(o);
                }
                continue;
            }

            if (isList) {
                List container = (List) object;
                data.add(new Entry(object, container));

                for (Object o : container) {
                    toProcess.add(o);
                }
                continue;
            }

            if (isClass) {
                Class<?> clazz = (Class)object;
                data.add(new Entry(object, Arrays.asList(clazz.getName())));
                continue;
            }

            if (!dataContainsKey(object)) {
                data.add(new Entry(object, new LinkedList<Fld>()));
            }
            for (Field field : getFields(object)) {
                if ((field.getModifiers() & Modifier.FINAL) != 0 && !field.getName().contains("this$")) continue;
                Object o = Reflection.field(field.getName()).ofType(field.getType()).in(object).get();
                toProcess.add(o);
                dataGet(object).add(new Fld(field.getName(), o));
            }
        }
        return toProcess;
    }

    private List<Map.Entry<Object, Object>> sort(Set<Map.Entry<Object, Object>> entries) {
        List<Map.Entry<Object, Object>> result = new LinkedList<Map.Entry<Object, Object>>(entries);
        Collections.sort(result, new Comparator<Map.Entry<Object, Object>>() {
            @Override
            public int compare(Map.Entry<Object, Object> o1, Map.Entry<Object, Object> o2) {
                return getString(o1).compareTo(getString(o2));
            }

            private String getString(Map.Entry<Object, Object> o) {
                if (o.getKey() instanceof String) {
                    return (String) o.getKey();
                } else {
                    return o.getKey().toString();
                }
            }
        });
        return result;
    }

    private List<Object> dataGet(Object object) {
        Key key = new Key(object);
        for (Entry entry : data) {
            if (entry.getKey().equals(key)) {
                return (List)entry.getValue();
            }
        }
        throw new RuntimeException("не найдено!");
    }

    private List<Field> getFields(Object object) {
        List<Field> result = new LinkedList<Field>();

        Class<?> clazz = object.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            result.addAll(Arrays.asList(fields));
            clazz = clazz.getSuperclass();
        } while (!Object.class.equals(clazz));

        Collections.sort(result, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return (o1.getType() + o1.getName()).compareTo(o2.getType() + o2.getName());
            }
        });
        return result;
    }

}
