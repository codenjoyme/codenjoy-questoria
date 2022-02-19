package apofig.compiler;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class JavaClass {
    private final Class clazz;

    public JavaClass(Class clazz) {
        this.clazz = clazz;
    }

    public Object newInstance(Object... constructorArgs) {
        try {
            return clazz.getConstructor(getTypes(constructorArgs)).newInstance(constructorArgs);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            try {
                return clazz.getConstructor(getPrimitiveTypes(constructorArgs)).newInstance(constructorArgs);
            } catch (InstantiationException e2) {
                throw new RuntimeException(e2);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2);
            } catch (NoSuchMethodException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    private Class[] getPrimitiveTypes(Object[] constructorArgs) {
        Class[] classes = getTypes(constructorArgs);
        for (int index=0; index < classes.length; index++) {
            if (classes[index].equals(Integer.class)) classes[index] = int.class; else
            if (classes[index].equals(Boolean.class)) classes[index] = boolean.class; else
            if (classes[index].equals(Character.class)) classes[index] = char.class; else
            if (classes[index].equals(Double.class)) classes[index] = double.class; else
            if (classes[index].equals(Byte.class)) classes[index] = byte.class; else
            if (classes[index].equals(Short.class)) classes[index] = short.class; else
            if (classes[index].equals(Long.class)) classes[index] = long.class; else
            if (classes[index].equals(Float.class)) classes[index] = float.class;
        }
        return classes;
    }

    private Class[] getTypes(Object[] constructorArgs) {
        List<Class> classes = new LinkedList<Class>();
        for (Object arg : constructorArgs) {
            classes.add(arg.getClass());
        }
        return classes.toArray(new Class[0]);
    }

    public Class getRealClass() {
        return clazz;
    }
}
