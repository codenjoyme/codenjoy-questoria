package apofig.saver;

import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import apofig.javaquest.services.PlayerServiceImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void list() {
        class B {
            int c;

            public B(int c) {
                this.c = c;
            }
        }

        class A {
            List<B> bs;
        }

        A a = new A();
        a.bs = Arrays.asList(new B(12), new B(23), new B(34));


        assertEquals(
                "A@0 = {\n" +
                "    bs = ArrayList@1\n" +
                "}\n" +
                "ArrayList@1 = {\n" +
                "    [0] = B@2\n" +
                "    [1] = B@3\n" +
                "    [2] = B@4\n" +
                "}\n" +
                "B@2 = {\n" +
                "    c = 12\n" +
                "}\n" +
                "B@3 = {\n" +
                "    c = 23\n" +
                "}\n" +
                "B@4 = {\n" +
                "    c = 34\n" +
                "}\n", new Saver().save(a));
    }

    @Test
    public void map() {
        class B {
            Integer c;

            public B(Integer c) {
                this.c = c;
            }
        }

        class A {
            Map<Object, B> bmap;
        }

        A a = new A();
        a.bmap = new HashMap<>();
        a.bmap.put(1, new B(11));
        a.bmap.put(new B(22), new B(23));
        a.bmap.put(3, new B(null));
        a.bmap.put(4, null);

        assertEquals(
                "A@0 = {\n" +
                "    bmap = HashMap@1\n" +
                "}\n" +
                "HashMap@1 = {\n" +
                "    [1] = B@2\n" +
                "    [3] = B@3\n" +
                "    [B@4] = B@5\n" +
                "    [4] = null\n" +
                "}\n" +
                "B@2 = {\n" +
                "    c = 11\n" +
                "}\n" +
                "B@3 = {\n" +
                "    c = null\n" +
                "}\n" +
                "B@4 = {\n" +
                "    c = 22\n" +
                "}\n" +
                "B@5 = {\n" +
                "    c = 23\n" +
                "}\n", new Saver().save(a));
    }

    @Test
    public void array() {
        class B {
            String c;

            public B(String c) {
                this.c = c;
            }
        }

        class A {
            B[] bs;
        }

        A a = new A();
        a.bs = new B[] {new B("11"), new B("22"), new B("33")};

        assertEquals(
                "A@0 = {\n" +
                "    bs = B[]@1\n" +
                "}\n" +
                "B[]@1 = {\n" +
                "    [0] = B@2\n" +
                "    [1] = B@3\n" +
                "    [2] = B@4\n" +
                "}\n" +
                "B@2 = {\n" +
                "    c = 11\n" +
                "}\n" +
                "B@3 = {\n" +
                "    c = 22\n" +
                "}\n" +
                "B@4 = {\n" +
                "    c = 33\n" +
                "}\n", new Saver().save(a));
    }

//    @Test
//    public void test() {
//        PlayerService service = new PlayerServiceImpl();
//        Player player1 = service.loadGame(service.register("player1"));
//        Player player2 = service.loadGame(service.register("player2"));
//
//        player1.getGame().getJoystick().moveDown();
//        player2.getGame().getJoystick().moveLeft();
//        service.nextStepForAllGames();
//
//        assertEquals(
//                " ", new Saver().save(service));
//    }
}
