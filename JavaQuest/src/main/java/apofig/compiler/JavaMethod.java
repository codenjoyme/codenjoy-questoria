package apofig.compiler;

import apofig.javaquest.field.object.monster.MethodRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaMethod implements MethodRunner{
    private Method method;
    private Object object;
    private String code;

    public JavaMethod(Method method, Object object, String code) {
        this.method = method;
        this.object = object;
        this.code = code;
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

    public String getCode() {
        return code;
    }
}
