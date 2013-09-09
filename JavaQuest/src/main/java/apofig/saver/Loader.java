package apofig.saver;

import org.fest.reflect.core.Reflection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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
            JSONArray objects = (JSONArray)json.get((String)json.keys().next());

            for (int index = objects.length() - 1; index >= 0; index--) {
                JSONObject object = (JSONObject)objects.get(index);
                String id = (String)object.get("id");
                String className = (String)object.get("type");

                Class<?> aClass = loadClass(className);

                Object newInstance = Reflection.constructor().in(aClass).newInstance();

                instances.put(id, newInstance);
            }

            for (int index = objects.length() - 1; index >= 0; index--) {
                JSONObject object = (JSONObject)objects.get(index);
                String id = (String)object.get("id");
                JSONArray fields = (JSONArray)object.get("fields");

                for (int jndex = 0; jndex < fields.length(); jndex++) {
                    JSONObject field = (JSONObject)fields.get(jndex);

                    String name = (String)field.keys().next();
                    String value = (String)field.get(name);

                    if (instances.containsKey(value)) {

                        Object parent = instances.get(id);
                        try {
                            parent.getClass().getDeclaredField(name).set(parent, instances.get(value));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (NoSuchFieldException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Class<?> loadClass(String className) {
        boolean isArray = className.startsWith("[L");
        if (isArray) {
            className = className.replace("[L", "");
        }

        try {
            Class<?> aClass = getClass().getClassLoader().loadClass(className);

//            Arrays.

            return aClass;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
