package apofig.saver;

import org.fest.reflect.core.Reflection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * User: sanja
 * Date: 09.09.13
 * Time: 17:29
 */
public class Loader {

    private Map<String, Object> instances = new HashMap<>();

    public Object load(String saved) {
        try {
            JSONObject json = new JSONObject(saved);
            Iterator keys = json.keys();
            JSONArray objects = (JSONArray) json.get((String) keys.next());
            String mainObjectId = (String)json.get((String)keys.next());

            for (int index = 0; index < objects.length(); index++) {
                JSONObject object = (JSONObject) objects.get(index);
                String id = (String) object.get("id");
                String className = (String) object.get("type");

                Object newInstance = null;
                if (isArray(className)) {
                    JSONArray fields = (JSONArray) object.get("fields");
                    if (className.equals("[C")) {     // TODO добавить все другие виды массивов и массивов массивов
                        newInstance = new char[((String)(fields.get(0))).length()];
                    } else if (className.equals("[[C")) {
                        newInstance = new char[fields.length()][];
                    } else {
                        newInstance = new Object[fields.length()];
                    }
                } else if (isList(className)) {
                    newInstance = new LinkedList();
                } else if (isMap(className)) {
                    newInstance = new HashMap();
                } else {
                    if (className.contains("$")) {
                        String mainClassName = className.substring(0, className.indexOf('$'));
                        String innerClassName = className.substring(className.indexOf('$') + 1, className.length());
                        Class<?> aClass = loadClass(mainClassName);
                        for (Class<?> innerClass : aClass.getDeclaredClasses()) {
                            if (innerClass.getSimpleName().equals(innerClassName)) {

                                JSONArray fields = (JSONArray) object.get("fields");
                                for (int i = 0; i < fields.length(); i++) {
                                    JSONObject field = (JSONObject)fields.get(i);
                                    String key = (String)field.keys().next();
                                    if (key.contains("this$")) {
                                        String value = ((String)field.get(key));
                                        Object parent = instances.get(value);

                                        newInstance = Reflection.constructor().withParameterTypes(parent.getClass()).in(innerClass).newInstance(parent);
                                    }
                                }
                            }
                        }
                    } else {
                        Class<?> aClass = loadClass(className);
                        newInstance = Reflection.constructor().in(aClass).newInstance();
                    }
                }
                instances.put(id, newInstance);
            }

            for (int index = 0; index < objects.length(); index++) {
                JSONObject object = (JSONObject) objects.get(index);
                String id = (String) object.get("id");
                JSONArray fields = (JSONArray) object.get("fields");

                for (int jndex = 0; jndex < fields.length(); jndex++) {
                    Object fld = fields.get(jndex);
                    if (fld instanceof String) {
                        Object container = instances.get(id);
                        if (List.class.isAssignableFrom(container.getClass())) {
                            if (instances.get(fld) == null) {
                                ((List) container).add(fld);
                            } else {
                                ((List) container).add(instances.get(fld));
                            }
                        }
                        if (container.getClass().getName().startsWith("[[C")) {     // TODO добавить все другие виды массивов и массивов массивов
                            char[][] array = (char[][])instances.get(id);
                            array[jndex] = (char[])instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[C")) {
                            char[] dest = (char[])container;
                            char[] source = ((String) fld).toCharArray();
                            for (int i = 0; i < source.length; i++) {
                                dest[i] = source[i];
                            }
                        }
                    } else {
                        JSONObject field = (JSONObject) fld;

                        String name = (String) field.keys().next();
                        String value = null;
                        Object o = field.get(name);
                        if (!o.equals(JSONObject.NULL)) {
                            value = (String) o;
                        }

                        Object parent = instances.get(id);
                        if (Map.class.isAssignableFrom(parent.getClass())) {
                            Map map = (Map)parent;
                            Object key = name;
                            if (name.contains("@")) {
                                key = instances.get(key);
                            }
                            Object val = value;
                            if (value.contains("@")) {
                                val = instances.get(val);
                            }
                            map.put(key, val);
                        } else if (!instances.containsKey(value)) {
                            if ((value instanceof String) && !value.contains("@")) {
                                Field declaredField = getField(parent.getClass(), name);
                                if (declaredField.getType().equals(int.class)) { // TODO как на счет других примитивов?
                                    setFieldValue(parent, name, Integer.valueOf(value));
                                } else if (declaredField.getType().equals(boolean.class)) {
                                    setFieldValue(parent, name, Boolean.valueOf(value));
                                } else {
                                    setFieldValue(parent, name, value);
                                }
                            }
                        } else {
                            Object objectValue = instances.get(value);
                            setFieldValue(parent, name, objectValue);
                        }
                    }
                }
            }

            return instances.get(mainObjectId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Object newInstance(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                try {
                    return constructor.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("Default конструктор не найден");
    }

    private Field getField(Class<?> clazz, String name) {
        if (Object.class.equals(clazz)) {
            throw new RuntimeException(String.format("Поле %s не найдено нигде в иерархии класса.", name));
        }
        Field declaredField = null;
        try {
            declaredField = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getField(clazz.getSuperclass(), name);
        }
        return declaredField;
    }

    private void setFieldValue(Object object, String name, Object fieldValue) {
        try {
            Field field = getField(object.getClass(), name);
            boolean a = field.isAccessible();
            field.setAccessible(true);
            field.set(object, fieldValue);
            field.setAccessible(a);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isMap(String className) {
        return Map.class.isAssignableFrom(getClass(className));
    }

    private boolean isList(String className) {
        return List.class.isAssignableFrom(getClass(className));
    }

    private Class<?> loadClass(String className) {
        boolean isArray = isArray(className);
        if (isArray) {
            return Object[].class;
        }

        return getClass(className);
    }

    private boolean isArray(String className) {
        return className.startsWith("[");
    }

    private Class<?> getClass(String className) {
        try {
            return getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
