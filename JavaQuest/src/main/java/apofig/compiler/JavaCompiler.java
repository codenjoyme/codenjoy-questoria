package apofig.compiler;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/18/13
 * Time: 12:19 PM
 */
public class JavaCompiler {
    public static int ID = 0;

    public JavaMethod getMethod(String code, Object... args) {
        String className = "Dynamic" + (ID++);
        String aClass = "public class " + className + " {" +
                code + "}";

        Object compile = null;
        try {
            compile = compile(className, aClass).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Method[] methods = compile.getClass().getDeclaredMethods();
        if (methods.length != 1) {
            throw new IllegalArgumentException("Expected one method!");
        }

        return new JavaMethod(methods[0], compile);
    }

    private Class compile(String fullName, String src) {
        // We get an instance of JavaCompiler. Then
        // we create a file manager
        // (our custom implementation of it)
        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = new
                ClassFileManager(compiler
                .getStandardFileManager(null, null, null));

        // Dynamic compiling requires specifying
        // a list of "files" to compile. In our case
        // this is a list containing one "file" which is in our case
        // our own implementation (see details below)
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(fullName, src));

        // We specify a task to the compiler. Compiler should use our file
        // manager and our list of "files".
        // Then we run the compilation with call()
        compiler.getTask(null, fileManager, null, null,
                null, jfiles).call();

        // Creating an instance of our compiled class and
        // running its toString() method
        try {
            return fileManager.getClassLoader(null)
                    .loadClass(fullName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public JavaClass newClass(String code) {
        String className = getClassName(code);
        Class clazz = compile(className, code);
        return new JavaClass(clazz);
    }

    private String getClassName(String code) {
        String[] split = code.split(" ");
        String result = null;
        int index = 0;
        do {
            result = split[index++];
        } while (result.equals("public") || result.equals("final") || result.equals("class"));

        return result;
    }
}
