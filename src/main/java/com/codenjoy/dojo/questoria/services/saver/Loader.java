package com.codenjoy.dojo.questoria.services.saver;

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

import com.codenjoy.dojo.services.settings.SettingsReader;
import org.fest.reflect.core.Reflection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Loader {

    private Map<String, Object> instances = new HashMap<>();

    public Object load(String saved) {
        try {
            JSONObject json = new JSONObject(saved);
            Iterator<String> keys = json.keys();
            JSONArray objects = (JSONArray) json.get(keys.next());
            String mainObjectId = (String)json.get(keys.next());

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
                    newInstance = new LinkedList<>();
                } else if (isMap(className)) {
                    newInstance = new HashMap<>();
                } else if (isSettings(className)) {
                    JSONArray fields = (JSONArray) object.get("fields");
                    String jsonValue = fields.getString(0);
                    try {
                        Class<?> aClass = loadClass(className);
                        SettingsReader settings = (SettingsReader) aClass.newInstance();
                        settings.update(new JSONObject(jsonValue));
                        newInstance = settings;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (className.contains("$")) {
                        String mainClassName = className.substring(0, className.indexOf('$'));
                        String innerClassName = className.substring(className.indexOf('$') + 1);
                        Class<?> aClass = loadClass(mainClassName);
                        if (isNumber(innerClassName)) {
                            throw new UnsupportedOperationException("Попытка загрузить аннонимный класс '" + className + "'. Не разобрался еще с этим..."); // TODO так разберись!
                        }
                        for (Class<?> innerClass : aClass.getDeclaredClasses()) {
                            if (innerClass.getSimpleName().equals(innerClassName)) {
                                if (object.has("value")) {
                                    // this is enum
                                    String value = (String) object.get("value");
                                    newInstance = Enum.valueOf((Class<? extends Enum>)innerClass, value);
                                } else if ((innerClass.getModifiers() & Modifier.STATIC) != 0) {
                                    newInstance = Reflection.constructor().in(innerClass).newInstance();
                                } else {
                                    JSONArray fields = (JSONArray) object.get("fields");
                                    for (int i = 0; i < fields.length(); i++) {
                                        JSONObject field = (JSONObject)fields.get(i);
                                        String key = field.keys().next();
                                        if (key.contains("this$")) {
                                            String value = ((String)field.get(key));
                                            Object parent = instances.get(value);

                                            newInstance = Reflection.constructor().withParameterTypes(parent.getClass()).in(innerClass).newInstance(parent);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (Class.class.getName().equals(className)) {
                            JSONArray fields = (JSONArray) object.get("fields");

                            newInstance = loadClass((String)fields.get(0));
                        } else {
                            Class<?> aClass = loadClass(className);
                            if (object.has("value")) {
                                // this is enum
                                String value = (String) object.get("value");
                                newInstance = Enum.valueOf((Class<? extends Enum>) aClass, value);
                            } else {
                                try {
                                    newInstance = Reflection.constructor().in(aClass).newInstance();
                                } catch (Exception e) {
                                    Map<Class<?>, Object> primitives = new HashMap<>();
                                    primitives.put(char.class, '\0');
                                    primitives.put(int.class, 0);
                                    primitives.put(boolean.class, false);
                                    primitives.put(double.class, 0.0);
                                    primitives.put(short.class, 0);
                                    primitives.put(String.class, "");

                                    Constructor<?>[] constructors = aClass.getConstructors();
                                    for (Constructor<?> constructor : constructors) {
                                        Class[] params = constructor.getParameterTypes();
                                        Object arguments[] = new Object[params.length];
                                        for (int i = 0; i < params.length; i++) {
                                            arguments[i] = primitives.get(params[i]);
                                        }
                                        try {
                                            newInstance = constructor.newInstance(arguments);
                                            break;
                                        } catch (Exception e2) {
                                            continue;
                                        }
                                    }
                                    if (newInstance == null) {
                                        throw new RuntimeException("Cant construct object for: " + aClass);
                                    }
                                }
                            }
                        }
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
                            char[] dest = (char[]) container;
                            char[] source = ((String) fld).toCharArray();
                            for (int i = 0; i < source.length; i++) {
                                dest[i] = source[i];
                            }
                        }
                    } else {
                        JSONObject field = (JSONObject) fld;

                        String name = field.keys().next();
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
                            if (value.equals("@NULL")) {
                                setFieldValue(parent, name, null);
                            } else if ((value instanceof String)) {
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

    private boolean isSettings(String className) {
        try {
            Class<?> aClass = getClass().getClassLoader().loadClass(className);
            return SettingsReader.class.isAssignableFrom(aClass);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getField(clazz.getSuperclass(), name);
        }
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
