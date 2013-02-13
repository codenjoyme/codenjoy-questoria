package apofig.compiler;

import apofig.javaquest.map.MethodRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * User: oleksandr.baglai
 * Date: 1/18/13
 * Time: 12:38 PM
 */
public class JavaMethod implements MethodRunner{
    private Method method;
    private Object object;

    public JavaMethod(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    @Override
    public Object run(Object...args) {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
