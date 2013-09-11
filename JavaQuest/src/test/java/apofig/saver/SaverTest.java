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

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$1A\",\"fields\":[{\"b\":\"B@1\"},{\"c\":\"C@2\"},{\"d\":\"D@3\"}]},{\"id\":\"B@1\",\"type\":\"apofig.saver.SaverTest$1B\",\"fields\":[{\"a\":\"C@2\"},{\"c\":\"C@4\"}]},{\"id\":\"C@2\",\"type\":\"apofig.saver.SaverTest$1C\",\"fields\":[{\"d\":\"D@3\"},{\"i\":\"4\"}]},{\"id\":\"D@3\",\"type\":\"apofig.saver.SaverTest$1D\",\"fields\":[{\"a\":\"5\"},{\"b\":\"6\"}]},{\"id\":\"C@4\",\"type\":\"apofig.saver.SaverTest$1C\",\"fields\":[{\"d\":null},{\"i\":\"0\"}]}],\"main\":\"A@0\"}",
                new Saver().save(a));
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

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$2A\",\"fields\":[{\"bs\":\"ArrayList@1\"}]},{\"id\":\"ArrayList@1\",\"type\":\"java.util.Arrays$ArrayList\",\"fields\":[\"B@2\",\"B@3\",\"B@4\"]},{\"id\":\"B@2\",\"type\":\"apofig.saver.SaverTest$2B\",\"fields\":[{\"c\":\"12\"}]},{\"id\":\"B@3\",\"type\":\"apofig.saver.SaverTest$2B\",\"fields\":[{\"c\":\"23\"}]},{\"id\":\"B@4\",\"type\":\"apofig.saver.SaverTest$2B\",\"fields\":[{\"c\":\"34\"}]}],\"main\":\"A@0\"}",
                new Saver().save(a));
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

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$3A\",\"fields\":[{\"bmap\":\"HashMap@1\"}]},{\"id\":\"HashMap@1\",\"type\":\"java.util.HashMap\",\"fields\":[{\"B@2\":\"B@3\"},{\"1\":\"B@4\"},{\"3\":\"B@5\"},{\"4\":null}]},{\"id\":\"B@2\",\"type\":\"apofig.saver.SaverTest$3B\",\"fields\":[{\"c\":\"22\"}]},{\"id\":\"B@3\",\"type\":\"apofig.saver.SaverTest$3B\",\"fields\":[{\"c\":\"23\"}]},{\"id\":\"B@4\",\"type\":\"apofig.saver.SaverTest$3B\",\"fields\":[{\"c\":\"11\"}]},{\"id\":\"B@5\",\"type\":\"apofig.saver.SaverTest$3B\",\"fields\":[{\"c\":null}]}],\"main\":\"A@0\"}",
                new Saver().save(a));
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

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$4A\",\"fields\":[{\"bs\":\"B[]@1\"}]},{\"id\":\"B[]@1\",\"type\":\"[Lapofig.saver.SaverTest$4B;\",\"fields\":[\"B@2\",\"B@3\",\"B@4\"]},{\"id\":\"B@2\",\"type\":\"apofig.saver.SaverTest$4B\",\"fields\":[{\"c\":\"11\"}]},{\"id\":\"B@3\",\"type\":\"apofig.saver.SaverTest$4B\",\"fields\":[{\"c\":\"22\"}]},{\"id\":\"B@4\",\"type\":\"apofig.saver.SaverTest$4B\",\"fields\":[{\"c\":\"33\"}]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }


    @Test
    public void primitiveIntArray() throws Exception {
        class A {
            int[] bs;
        }

        A a = new A();
        a.bs = new int[] {10, 11, 12};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$5A\",\"fields\":[{\"bs\":\"int[]@1\"}]},{\"id\":\"int[]@1\",\"type\":\"[I\",\"fields\":[\"10\",\"11\",\"12\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveCharArray() throws Exception {
        class A {
            char[] bs;
        }

        A a = new A();
        a.bs = new char[] {'A', 'B', 'C'};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$6A\",\"fields\":[{\"bs\":\"char[]@1\"}]},{\"id\":\"char[]@1\",\"type\":\"[C\",\"fields\":[\"ABC\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveBooleanArray() throws Exception {
        class A {
            boolean[] bs;
        }

        A a = new A();
        a.bs = new boolean[] {true, false, true};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$7A\",\"fields\":[{\"bs\":\"boolean[]@1\"}]},{\"id\":\"boolean[]@1\",\"type\":\"[Z\",\"fields\":[\"true\",\"false\",\"true\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveLongArray() throws Exception {
        class A {
            long[] bs;
        }

        A a = new A();
        a.bs = new long[] {1L, 2L, 3l};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$8A\",\"fields\":[{\"bs\":\"long[]@1\"}]},{\"id\":\"long[]@1\",\"type\":\"[J\",\"fields\":[\"1\",\"2\",\"3\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveByteArray() throws Exception {
        class A {
            byte[] bs;
        }

        A a = new A();
        a.bs = new byte[] {1, 2, 3};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$9A\",\"fields\":[{\"bs\":\"byte[]@1\"}]},{\"id\":\"byte[]@1\",\"type\":\"[B\",\"fields\":[\"1\",\"2\",\"3\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveDoubleArray() throws Exception {
        class A {
            double[] bs;
        }

        A a = new A();
        a.bs = new double[] {1, 2, 3};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$10A\",\"fields\":[{\"bs\":\"double[]@1\"}]},{\"id\":\"double[]@1\",\"type\":\"[D\",\"fields\":[\"1.0\",\"2.0\",\"3.0\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveFloatArray() throws Exception {
        class A {
            float[] bs;
        }

        A a = new A();
        a.bs = new float[] {1, 2, 3};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$11A\",\"fields\":[{\"bs\":\"float[]@1\"}]},{\"id\":\"float[]@1\",\"type\":\"[F\",\"fields\":[\"1.0\",\"2.0\",\"3.0\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
    }

    @Test
    public void primitiveShortArray() throws Exception {
        class A {
            short[] bs;
        }

        A a = new A();
        a.bs = new short[] {1, 2, 3};

        assertEquals("{\"objects\":[{\"id\":\"A@0\",\"type\":\"apofig.saver.SaverTest$12A\",\"fields\":[{\"bs\":\"short[]@1\"}]},{\"id\":\"short[]@1\",\"type\":\"[S\",\"fields\":[\"1\",\"2\",\"3\"]}],\"main\":\"A@0\"}",
                new Saver().save(a));
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

        assertEquals("{\"objects\":[{\"id\":\"PlayerServiceImpl@0\",\"type\":\"apofig.javaquest.services.PlayerServiceImpl\",\"fields\":[{\"game\":\"JavaQuest@1\"},{\"players\":\"HashMap@2\"}]},{\"id\":\"JavaQuest@1\",\"type\":\"apofig.javaquest.map.JavaQuest\",\"fields\":[{\"initPosition\":\"PointImpl@4\"},{\"map\":\"TerritoryMapImpl@5\"},{\"objects\":\"ObjectFactoryImpl@6\"},{\"players\":\"LinkedList@7\"},{\"viewSize\":\"41\"}]},{\"id\":\"HashMap@2\",\"type\":\"java.util.HashMap\",\"fields\":[{\"player1-493567632\":\"Player@3\"},{\"player2-493567631\":\"Player@9\"}]},{\"id\":\"Player@3\",\"type\":\"apofig.javaquest.services.Player\",\"fields\":[{\"game\":\"JavaQuestSinglePlayer@10\"},{\"gameCode\":\"player1-493567632\"},{\"name\":\"player1\"}]},{\"id\":\"PointImpl@4\",\"type\":\"apofig.javaquest.map.PointImpl\",\"fields\":[{\"x\":\"93\"},{\"y\":\"482\"}]},{\"id\":\"TerritoryMapImpl@5\",\"type\":\"apofig.javaquest.map.TerritoryMapImpl\",\"fields\":[{\"map\":\"Map@11\"},{\"objects\":\"ObjectFactoryImpl@6\"},{\"fogs\":\"HashMap@12\"},{\"height\":\"564\"},{\"width\":\"660\"}]},{\"id\":\"ObjectFactoryImpl@6\",\"type\":\"apofig.javaquest.map.object.ObjectFactoryImpl\",\"fields\":[{\"loader\":\"ObjectLoader@14\"},{\"monsters\":\"MonsterFactoryImpl@15\"},{\"objects\":\"HashMap@16\"}]},{\"id\":\"LinkedList@7\",\"type\":\"java.util.LinkedList\",\"fields\":[\"Me@8\",\"Me@38\"]},{\"id\":\"Me@8\",\"type\":\"apofig.javaquest.map.object.Me\",\"fields\":[{\"view\":\"PlayerView@18\"},{\"info\":\"Player@19\"},{\"whereToGo\":null},{\"map\":\"TerritoryMapImpl@5\"},{\"world\":\"WorldImpl@17\"},{\"x\":\"93\"},{\"y\":\"481\"}]},{\"id\":\"Player@9\",\"type\":\"apofig.javaquest.services.Player\",\"fields\":[{\"game\":\"JavaQuestSinglePlayer@20\"},{\"gameCode\":\"player2-493567631\"},{\"name\":\"player2\"}]},{\"id\":\"JavaQuestSinglePlayer@10\",\"type\":\"apofig.javaquest.map.JavaQuestSinglePlayer\",\"fields\":[{\"game\":\"JavaQuest@1\"},{\"player\":\"Me@8\"}]},{\"id\":\"Map@11\",\"type\":\"apofig.javaquest.map.Map\",\"fields\":[{\"map\":\"char[][]@21\"}]},{\"id\":\"HashMap@12\",\"type\":\"java.util.HashMap\",\"fields\":[{\"Me@8\":\"Map@13\"},{\"Me@38\":\"Map@22\"}]},{\"id\":\"Map@13\",\"type\":\"apofig.javaquest.map.Map\",\"fields\":[{\"map\":\"char[][]@23\"}]},{\"id\":\"ObjectLoader@14\",\"type\":\"apofig.javaquest.map.object.ObjectLoader\",\"fields\":[{\"cache\":\"HashMap@24\"}]},{\"id\":\"MonsterFactoryImpl@15\",\"type\":\"apofig.javaquest.map.object.monster.MonsterFactoryImpl\",\"fields\":[{\"monster\":null},{\"count\":\"10\"},{\"monsters\":\"ArrayList@25\"}]},{\"id\":\"HashMap@16\",\"type\":\"java.util.HashMap\",\"fields\":[{\"Me@8\":\"WorldImpl@17\"},{\"Me@38\":\"WorldImpl@35\"}]},{\"id\":\"WorldImpl@17\",\"type\":\"apofig.javaquest.map.object.WorldImpl\",\"fields\":[{\"name\":\"Me\"},{\"factory\":\"ObjectFactoryImpl@6\"},{\"place\":\"MapPlace@36\"}]},{\"id\":\"PlayerView@18\",\"type\":\"apofig.javaquest.map.PlayerView\",\"fields\":[{\"mask\":\"char[][]@37\"},{\"size\":\"41\"},{\"vx\":\"73\"},{\"vy\":\"462\"}]},{\"id\":\"Player@19\",\"type\":\"apofig.javaquest.map.Player\",\"fields\":[{\"name\":\"player1\"},{\"gold\":\"0\"}]},{\"id\":\"JavaQuestSinglePlayer@20\",\"type\":\"apofig.javaquest.map.JavaQuestSinglePlayer\",\"fields\":[{\"game\":\"JavaQuest@1\"},{\"player\":\"Me@38\"}]},{\"id\":\"Map@22\",\"type\":\"apofig.javaquest.map.Map\",\"fields\":[{\"map\":\"char[][]@39\"}]},{\"id\":\"HashMap@24\",\"type\":\"java.util.HashMap\",\"fields\":[{\"#\":\"class apofig.javaquest.map.object.impl.Wall\"},{\"$\":\"class apofig.javaquest.map.object.impl.Gold\"},{\"*\":\"class apofig.javaquest.map.object.impl.dron.Dron\"},{\"M\":\"class apofig.javaquest.map.object.impl.dron.DronMentor\"}]},{\"id\":\"ArrayList@25\",\"type\":\"java.util.ArrayList\",\"fields\":[\"FizzBuzzMonster@26\",\"PrimeFactoryMonster@27\",\"FibonacciNumbersMonster@28\",\"SumSquareDifferenceMonster@29\",\"XthPrimeMonster@30\",\"PowerDigitSumMonster@31\",\"FactorialMonster@32\",\"LongDivisionMonster@33\",\"MakeBricksMonster@34\"]},{\"id\":\"WorldImpl@35\",\"type\":\"apofig.javaquest.map.object.WorldImpl\",\"fields\":[{\"name\":\"Me\"},{\"factory\":\"ObjectFactoryImpl@6\"},{\"place\":\"MapPlace@40\"}]},{\"id\":\"MapPlace@36\",\"type\":\"apofig.javaquest.map.MapPlace\",\"fields\":[{\"map\":\"Map@11\"},{\"x\":\"93\"},{\"y\":\"481\"}]},{\"id\":\"Me@38\",\"type\":\"apofig.javaquest.map.object.Me\",\"fields\":[{\"view\":\"PlayerView@41\"},{\"info\":\"Player@42\"},{\"whereToGo\":null},{\"map\":\"TerritoryMapImpl@5\"},{\"world\":\"WorldImpl@35\"},{\"x\":\"93\"},{\"y\":\"482\"}]},{\"id\":\"MapPlace@40\",\"type\":\"apofig.javaquest.map.MapPlace\",\"fields\":[{\"map\":\"Map@11\"},{\"x\":\"93\"},{\"y\":\"482\"}]},{\"id\":\"PlayerView@41\",\"type\":\"apofig.javaquest.map.PlayerView\",\"fields\":[{\"mask\":\"char[][]@43\"},{\"size\":\"41\"},{\"vx\":\"74\"},{\"vy\":\"462\"}]},{\"id\":\"Player@42\",\"type\":\"apofig.javaquest.map.Player\",\"fields\":[{\"name\":\"player2\"},{\"gold\":\"0\"}]}],\"main\":\"PlayerServiceImpl@0\"}",
                actual);
    }
}
