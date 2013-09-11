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
                    newInstance = new Object[fields.length()];
                } else if (isList(className)) {
                    newInstance = new LinkedList();
                } else if (isMap(className)) {
                    newInstance = new HashMap();
                } else {
                    Class<?> aClass = loadClass(className);
                    newInstance = Reflection.constructor().in(aClass).newInstance();
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
                            ((List) container).add(fld);
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
                                Field declaredField = null;
                                try {
                                    declaredField = parent.getClass().getDeclaredField(name);
                                } catch (NoSuchFieldException e) {
                                    throw new RuntimeException(e);
                                }
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

    private void setFieldValue(Object parent, String name, Object fieldValue) {
        try {
            Field declaredField = parent.getClass().getDeclaredField(name);
            declaredField.setAccessible(true);
            declaredField.set(parent, fieldValue);
            declaredField.setAccessible(false);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e + ": for field " + name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e + ": for field " + name);
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
        return className.startsWith("[L");
    }

    private Class<?> getClass(String className) {
        try {
            return getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}