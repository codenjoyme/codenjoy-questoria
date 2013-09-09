package apofig.saver;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: sanja
 * Date: 09.09.13
 * Time: 4:21
 */
public class SaverTest {

    @Test
    public void simple() {
        class D {
            int a, b;
        }

        class C {
            D d;
            int i;
        }

        class B {
            C a;
            C c;
        }

        class A {
            C c;
            B b;
            D d;
        }

        D d = new D();
        d.a = 5;
        d.b = 6;

        C c = new C();
        c.d = d;
        c.i = 4;

        B b = new B();
        b.a = c;
        b.c = new C();

        A a = new A();
        a.b = b;
        a.c = c;
        a.d = d;

        assertEquals(
                "A@0 = {\n" +
                "    b = B@1\n" +
                "    c = C@2\n" +
                "    d = D@3\n" +
                "}\n" +
                "B@1 = {\n" +
                "    a = C@2\n" +
                "    c = C@4\n" +
                "}\n" +
                "C@2 = {\n" +
                "    d = D@3\n" +
                "    i = 4\n" +
                "}\n" +
                "D@3 = {\n" +
                "    a = 5\n" +
                "    b = 6\n" +
                "}\n" +
                "C@4 = {\n" +
                "    d = null\n" +
                "    i = 0\n" +
                "}\n", new Saver().save(a));
    }
}
