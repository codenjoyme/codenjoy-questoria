package apofig.saver;

import apofig.javaquest.map.object.monster.Monster;
import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import apofig.javaquest.services.PlayerServiceImpl;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: sanja
 * Date: 09.09.13
 * Time: 4:21
 */
public class SaverTest {

    @Test
    public void simple() throws Exception {
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

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void list() throws Exception {
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

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void map() throws Exception {
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

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void array() throws Exception {
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

        Approvals.verify(new Saver().save(a));
    }


    @Test
    public void primitiveIntArray() throws Exception {
        class A {
            int[] bs;
        }

        A a = new A();
        a.bs = new int[] {10, 11, 12};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveCharArray() throws Exception {
        class A {
            char[] bs;
        }

        A a = new A();
        a.bs = new char[] {'A', 'B', 'C'};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveBooleanArray() throws Exception {
        class A {
            boolean[] bs;
        }

        A a = new A();
        a.bs = new boolean[] {true, false, true};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveLongArray() throws Exception {
        class A {
            long[] bs;
        }

        A a = new A();
        a.bs = new long[] {1L, 2L, 3l};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveByteArray() throws Exception {
        class A {
            byte[] bs;
        }

        A a = new A();
        a.bs = new byte[] {1, 2, 3};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveDoubleArray() throws Exception {
        class A {
            double[] bs;
        }

        A a = new A();
        a.bs = new double[] {1, 2, 3};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveFloatArray() throws Exception {
        class A {
            float[] bs;
        }

        A a = new A();
        a.bs = new float[] {1, 2, 3};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void primitiveShortArray() throws Exception {
        class A {
            short[] bs;
        }

        A a = new A();
        a.bs = new short[] {1, 2, 3};

        Approvals.verify(new Saver().save(a));
    }

    @Test
    public void test() throws Exception {
        PlayerService service = new PlayerServiceImpl();
        Player player1 = service.loadGame(service.register("player1"));
        Player player2 = service.loadGame(service.register("player2"));

        player1.getGame().getJoystick().moveDown();
        player2.getGame().getJoystick().moveLeft();
        service.nextStepForAllGames();

        String actual = new Saver().exclude(char[][].class, char[].class).excludeChildren(Monster.class).save(service);

        Approvals.verify(actual);
    }
}
