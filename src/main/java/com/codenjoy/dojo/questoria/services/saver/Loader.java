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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Loader {

    private Map<String, Object> instances = new HashMap<>();
    private Map<String, Info> infos = new HashMap<>();

    public static class Info {
        String id;
        String className;
        JSONArray fields;
        String value;
    }

    public Object load(String saved) {
        try {
            JSONObject json = new JSONObject(saved);
            Iterator<String> keys = json.keys();
            JSONArray objects = (JSONArray) json.get(keys.next());
            String mainObjectId = (String) json.get(keys.next());

            for (int index = 0; index < objects.length(); index++) {
                Info info = new Info();

                JSONObject object = (JSONObject) objects.get(index);
                info.id = (String) object.get("id");
                info.className = (String) object.get("type");
                info.fields = (JSONArray) object.get("fields");
                if (object.has("value")) {
                    // this is enum
                    info.value = (String) object.get("value");
                }

                infos.put(info.id, info);
            }

            for (Info info : infos.values()) {
                String id = info.id;
                String className = info.className;
                JSONArray fields = info.fields;

                Object newInstance = null;
                if (isArray(className)) {
                    if (className.equals("[S")) {
                        newInstance = new short[fields.length()];
                    } else if (className.equals("[[S")) {
                        newInstance = new short[fields.length()][];
                    } else if (className.equals("[I")) {
                        newInstance = new int[fields.length()];
                    } else if (className.equals("[[I")) {
                        newInstance = new int[fields.length()][];
                    } else if (className.equals("[F")) {
                        newInstance = new float[fields.length()];
                    } else if (className.equals("[[F")) {
                        newInstance = new float[fields.length()][];
                    } else if (className.equals("[D")) {
                        newInstance = new double[fields.length()];
                    } else if (className.equals("[[D")) {
                        newInstance = new double[fields.length()][];
                    } else if (className.equals("[Z")) {
                        newInstance = new boolean[fields.length()];
                    } else if (className.equals("[[Z")) {
                        newInstance = new boolean[fields.length()][];
                    } else if (className.equals("[B")) {
                        newInstance = new byte[fields.length()];
                    } else if (className.equals("[[B")) {
                        newInstance = new byte[fields.length()][];
                    } else if (className.equals("[J")) {
                        newInstance = new long[fields.length()];
                    } else if (className.equals("[[J")) {
                        newInstance = new long[fields.length()][];
                    } else if (className.equals("[C")) {
                        newInstance = new char[fields.getString(0).length()];
                    } else if (className.equals("[[C")) {
                        newInstance = new char[fields.length()][];
                    } else if (className.startsWith("[L")) {
                        String type = className.substring("[L".length(), className.length() - 1);
                        Class<?> aClass = getClass(type);
                        int size = fields.length();
                        newInstance = Array.newInstance(aClass, size);
                    } else if (className.startsWith("[[L")) {
                        String type = className.substring("[[L".length(), className.length() - 1);
                        Class<?> aClass = getClass(type);
                        int[] dimensions = new int[]{
                                fields.length(),
                                infos.get(fields.getString(0)).fields.length()
                        };
                        newInstance = Array.newInstance(aClass, dimensions);
                    } else {
                        newInstance = new Object[fields.length()];
                    }
                } else if (isList(className)) {
                    newInstance = newInstance(getClass(className));
                } else if (isMap(className)) {
                    newInstance = newInstance(getClass(className));
                } else if (isSettings(className)) {
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
                            throw new UnsupportedOperationException("Попытка загрузить анонимный класс '" + className + "'. Не разобрался еще с этим..."); // TODO так разберись!
                        }
                        for (Class<?> innerClass : aClass.getDeclaredClasses()) {
                            if (innerClass.getSimpleName().equals(innerClassName)) {
                                if (info.value != null) {
                                    // this is enum
                                    newInstance = Enum.valueOf((Class<? extends Enum>) innerClass, info.value);
                                } else if ((innerClass.getModifiers() & Modifier.STATIC) != 0) {
                                    newInstance = newInstance(innerClass);
                                } else {
                                    for (int i = 0; i < fields.length(); i++) {
                                        JSONObject field = (JSONObject) fields.get(i);
                                        String key = field.keys().next();
                                        if (key.contains("this$")) {
                                            String value = ((String) field.get(key));
                                            Object parent = instances.get(value);

                                            newInstance = Reflection.constructor().withParameterTypes(parent.getClass()).in(innerClass).newInstance(parent);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (Class.class.getName().equals(className)) {
                            newInstance = loadClass((String) fields.get(0));
                        } else {
                            Class<?> aClass = loadClass(className);
                            if (info.value != null) {
                                // this is enum
                                newInstance = Enum.valueOf((Class<? extends Enum>) aClass, info.value);
                            } else {
                                newInstance = newInstance(aClass);
                            }
                        }
                    }
                }
                instances.put(id, newInstance);
            }

            for (Info info : infos.values()) {
                String id = info.id;
                JSONArray fields = info.fields;

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

                        // short
                        if (container.getClass().getName().startsWith("[[S")) {
                            short[][] array = (short[][]) instances.get(id);
                            array[jndex] = (short[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[S")) {
                            short[] dest = (short[]) container;
                            dest[jndex] = Short.parseShort((String) fld);
                        }

                        // int
                        if (container.getClass().getName().startsWith("[[I")) {
                            int[][] array = (int[][]) instances.get(id);
                            array[jndex] = (int[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[I")) {
                            int[] dest = (int[]) container;
                            dest[jndex] = Integer.parseInt((String) fld);
                        }

                        // float
                        if (container.getClass().getName().startsWith("[[F")) {
                            float[][] array = (float[][]) instances.get(id);
                            array[jndex] = (float[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[F")) {
                            float[] dest = (float[]) container;
                            dest[jndex] = Float.parseFloat((String) fld);
                        }

                        // double
                        if (container.getClass().getName().startsWith("[[D")) {
                            double[][] array = (double[][]) instances.get(id);
                            array[jndex] = (double[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[D")) {
                            double[] dest = (double[]) container;
                            dest[jndex] = Double.parseDouble((String) fld);
                        }

                        // boolean
                        if (container.getClass().getName().startsWith("[[Z")) {
                            boolean[][] array = (boolean[][]) instances.get(id);
                            array[jndex] = (boolean[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[Z")) {
                            boolean[] dest = (boolean[]) container;
                            dest[jndex] = Boolean.parseBoolean((String) fld);
                        }

                        // byte
                        if (container.getClass().getName().startsWith("[[B")) {
                            byte[][] array = (byte[][]) instances.get(id);
                            array[jndex] = (byte[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[B")) {
                            byte[] dest = (byte[]) container;
                            dest[jndex] = Byte.parseByte((String) fld);
                        }

                        // long
                        if (container.getClass().getName().startsWith("[[J")) {
                            long[][] array = (long[][]) instances.get(id);
                            array[jndex] = (long[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[J")) {
                            long[] dest = (long[]) container;
                            dest[jndex] = Long.parseLong((String) fld);
                        }

                        // char
                        if (container.getClass().getName().startsWith("[[C")) {
                            char[][] array = (char[][]) instances.get(id);
                            array[jndex] = (char[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[C")) {
                            char[] dest = (char[]) container;
                            char[] source = ((String) fld).toCharArray();
                            for (int i = 0; i < source.length; i++) {
                                dest[i] = source[i];
                            }
                        }

                        // object
                        if (container.getClass().getName().startsWith("[[L")) {
                            Object[][] array = (Object[][]) instances.get(id);
                            array[jndex] = (Object[]) instances.get(fld);
                        }
                        if (container.getClass().getName().startsWith("[L")) {
                            Object[] dest = (Object[]) container;
                            dest[jndex] = instances.get(fld);
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
                            Map map = (Map) parent;
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
                                Class<?> type = declaredField.getType();
                                if (type.equals(double.class) || type.equals(Double.class)) {
                                    setFieldValue(parent, name, Double.valueOf(value));
                                } else if (type.equals(float.class) || type.equals(Float.class)) {
                                    setFieldValue(parent, name, Float.valueOf(value));
                                } else if (type.equals(short.class) || type.equals(Short.class)) {
                                    setFieldValue(parent, name, Short.valueOf(value));
                                } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                                    setFieldValue(parent, name, Byte.valueOf(value));
                                } else if (type.equals(long.class) || type.equals(Long.class)) {
                                    setFieldValue(parent, name, Long.valueOf(value));
                                } else if (type.equals(int.class) || type.equals(Integer.class)) {
                                    setFieldValue(parent, name, Integer.valueOf(value));
                                } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                                    setFieldValue(parent, name, Boolean.valueOf(value));
                                } else if (type.equals(char.class) || type.equals(Character.class)) {
                                    setFieldValue(parent, name, value.charAt(0));
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

    private static Object newInstance(Class<?> aClass) {
        try {
            return Reflection.constructor().in(aClass).newInstance();
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
                    return constructor.newInstance(arguments);
                } catch (Exception e2) {
                    continue;
                }
            }
        }
        throw new RuntimeException("Cant construct object for: " + aClass);
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
