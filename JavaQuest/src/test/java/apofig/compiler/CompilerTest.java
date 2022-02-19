package apofig.compiler;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CompilerTest {

    private JavaCompiler compiler = new JavaCompiler();

    @Test
    public void shouldRunMethodWithoutParameters() {
        JavaCompiler compiler = new JavaCompiler();
        JavaMethod method = compiler.getMethod(
                "public String qwe() { \n" +
                "    return \"Hello world!\";\n" +
                "}");
            String result = (String)method.run();

        assertEquals("Hello world!", result);
    }

    @Test
    public void shouldRunMethodWithTwoStrings() {
        JavaMethod method = compiler.getMethod(
                "public String qwe(String qwe, String asd) { \n" +
                "    return \"The first is '\" + qwe + \"', the second is '\" + asd + \"'\";\n" +
                "}");
        String result = (String)method.run("qwe_string", "asd_string");

        assertEquals("The first is 'qwe_string', the second is 'asd_string'", result);
    }

    @Test
    public void shouldRunMethodWithTwoInts() {
        JavaMethod method = compiler.getMethod(
                "public String qwe(int qwe, int asd) { \n" +
                "    return \"The first is '\" + qwe + \"', the second is '\" + asd + \"'\";\n" +
                "}");
        assertEquals("The first is '1', the second is '2'", (String)method.run(1, 2));
        assertEquals("The first is '2', the second is '3'", (String)method.run(2, 3));
    }

    @Test
    public void shouldCompileClass() throws Exception {
        JavaClass clazz = compiler.newClass(
                "public class SomeDynaClass {\n" +
                "    public String qwe(int qwe, int asd) { \n" +
                "        return \"The first is '\" + qwe + \"', the second is '\" + asd + \"'\";\n" +
                "    }\n" +
                "    public String asd(String zxc) { \n" +
                "        return \"The third is '\" + zxc + \"'\";\n" +
                "    }\n" +
                "}");
        Class realClass = clazz.getRealClass();
        Object object = clazz.newInstance();

        String result = (String) realClass.getMethod("qwe", int.class, int.class).invoke(object, 1, 2);
        assertEquals("The first is '1', the second is '2'", result);

        result = (String) realClass.getMethod("asd", String.class).invoke(object, "string");
        assertEquals("The third is 'string'", result);
    }

    @Test
    public void shouldCompileClassWithoutDefaultConstructor() throws Exception {
        JavaClass clazz = compiler.newClass(
                "public class SomeDynaClass {\n" +
                "    private int i;\n" +
                "    public SomeDynaClass(int i) {\n" +
                "        this.i = i;\n" +
                "    }\n" +
                "    public String qwe(int qwe, int asd) { \n" +
                "        return \"The first is '\" + (qwe+i) + \"', the second is '\" + (asd+i) + \"'\";\n" +
                "    }\n" +
                "    public String asd(String zxc) { \n" +
                "        return \"The third is '\" + zxc + i + \"'\";\n" +
                "    }\n" +
                "}");
        Class realClass = clazz.getRealClass();
        Object object = clazz.newInstance(10);

        String result = (String) realClass.getMethod("qwe", int.class, int.class).invoke(object, 1, 2);
        assertEquals("The first is '11', the second is '12'", result);

        result = (String) realClass.getMethod("asd", String.class).invoke(object, "string");
        assertEquals("The third is 'string10'", result);
    }

}
