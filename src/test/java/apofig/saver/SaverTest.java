package apofig.saver;

import org.json.SortedJSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codenjoy.dojo.client.Utils.clean;
import static org.junit.Assert.assertEquals;

public class SaverTest {     // TODO переместить все в LoaderTest и переназвать его, а все иннерклассы вынести наружу в пакет dummy

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

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$1A','fields':[{'b':'B@1'},{'c':'C@2'},{'d':'D@3'},{'this$0':'SaverTest@4'}]},{'id':'B@1','type':'apofig.saver.SaverTest$1B','fields':[{'a':'C@2'},{'c':'C@5'},{'this$0':'SaverTest@4'}]},{'id':'C@2','type':'apofig.saver.SaverTest$1C','fields':[{'d':'D@3'},{'this$0':'SaverTest@4'},{'i':'4'}]},{'id':'D@3','type':'apofig.saver.SaverTest$1D','fields':[{'this$0':'SaverTest@4'},{'a':'5'},{'b':'6'}]},{'id':'SaverTest@4','type':'apofig.saver.SaverTest','fields':[]},{'id':'C@5','type':'apofig.saver.SaverTest$1C','fields':[{'d':'@NULL'},{'this$0':'SaverTest@4'},{'i':'0'}]}],'main':'A@0'}");
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

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$2A','fields':[{'this$0':'SaverTest@1'},{'bs':'ArrayList@2'}]},{'id':'SaverTest@1','type':'apofig.saver.SaverTest','fields':[]},{'id':'ArrayList@2','type':'java.util.Arrays$ArrayList','fields':['B@3','B@4','B@5']},{'id':'B@3','type':'apofig.saver.SaverTest$2B','fields':[{'this$0':'SaverTest@1'},{'c':'12'}]},{'id':'B@4','type':'apofig.saver.SaverTest$2B','fields':[{'this$0':'SaverTest@1'},{'c':'23'}]},{'id':'B@5','type':'apofig.saver.SaverTest$2B','fields':[{'this$0':'SaverTest@1'},{'c':'34'}]}],'main':'A@0'}");
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

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$3A','fields':[{'this$0':'SaverTest@1'},{'bmap':'HashMap@2'}]},{'id':'SaverTest@1','type':'apofig.saver.SaverTest','fields':[]},{'id':'HashMap@2','type':'java.util.HashMap','fields':[{'1':'B@3'},{'3':'B@4'},{'4':'@NULL'},{'B@5':'B@6'}]},{'id':'B@3','type':'apofig.saver.SaverTest$3B','fields':[{'this$0':'SaverTest@1'},{'c':'11'}]},{'id':'B@4','type':'apofig.saver.SaverTest$3B','fields':[{'this$0':'SaverTest@1'},{'c':'@NULL'}]},{'id':'B@5','type':'apofig.saver.SaverTest$3B','fields':[{'this$0':'SaverTest@1'},{'c':'22'}]},{'id':'B@6','type':'apofig.saver.SaverTest$3B','fields':[{'this$0':'SaverTest@1'},{'c':'23'}]}],'main':'A@0'}");
    }

    private void assertSave(Object object, String expected) {
        assertEquals(sort(expected),
                clean(new Saver().save(object)));
    }

    public static String sort(String expected) {
        return clean(new SortedJSONObject(expected).toString());
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

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$4A','fields':[{'bs':'B[]@1'},{'this$0':'SaverTest@2'}]},{'id':'B[]@1','type':'[Lapofig.saver.SaverTest$4B;','fields':['B@3','B@4','B@5']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]},{'id':'B@3','type':'apofig.saver.SaverTest$4B','fields':[{'this$0':'SaverTest@2'},{'c':'11'}]},{'id':'B@4','type':'apofig.saver.SaverTest$4B','fields':[{'this$0':'SaverTest@2'},{'c':'22'}]},{'id':'B@5','type':'apofig.saver.SaverTest$4B','fields':[{'this$0':'SaverTest@2'},{'c':'33'}]}],'main':'A@0'}");
    }


    @Test
    public void primitiveIntArray() {
        class A {
            int[] bs;
        }

        A a = new A();
        a.bs = new int[] {10, 11, 12};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$5A','fields':[{'bs':'int[]@1'},{'this$0':'SaverTest@2'}]},{'id':'int[]@1','type':'[I','fields':['10','11','12']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveCharArray() {
        class A {
            char[] bs;
        }

        A a = new A();
        a.bs = new char[] {'A', 'B', 'C'};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$6A','fields':[{'bs':'char[]@1'},{'this$0':'SaverTest@2'}]},{'id':'char[]@1','type':'[C','fields':['ABC']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveBooleanArray() {
        class A {
            boolean[] bs;
        }

        A a = new A();
        a.bs = new boolean[] {true, false, true};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$7A','fields':[{'bs':'boolean[]@1'},{'this$0':'SaverTest@2'}]},{'id':'boolean[]@1','type':'[Z','fields':['true','false','true']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveLongArray() {
        class A {
            long[] bs;
        }

        A a = new A();
        a.bs = new long[] {1L, 2L, 3l};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$8A','fields':[{'bs':'long[]@1'},{'this$0':'SaverTest@2'}]},{'id':'long[]@1','type':'[J','fields':['1','2','3']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveByteArray() {
        class A {
            byte[] bs;
        }

        A a = new A();
        a.bs = new byte[] {1, 2, 3};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$9A','fields':[{'bs':'byte[]@1'},{'this$0':'SaverTest@2'}]},{'id':'byte[]@1','type':'[B','fields':['1','2','3']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveDoubleArray() {
        class A {
            double[] bs;
        }

        A a = new A();
        a.bs = new double[] {1, 2, 3};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$10A','fields':[{'bs':'double[]@1'},{'this$0':'SaverTest@2'}]},{'id':'double[]@1','type':'[D','fields':['1.0','2.0','3.0']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveFloatArray() {
        class A {
            float[] bs;
        }

        A a = new A();
        a.bs = new float[] {1, 2, 3};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$11A','fields':[{'bs':'float[]@1'},{'this$0':'SaverTest@2'}]},{'id':'float[]@1','type':'[F','fields':['1.0','2.0','3.0']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void primitiveShortArray() {
        class A {
            short[] bs;
        }

        A a = new A();
        a.bs = new short[] {1, 2, 3};

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$12A','fields':[{'bs':'short[]@1'},{'this$0':'SaverTest@2'}]},{'id':'short[]@1','type':'[S','fields':['1','2','3']},{'id':'SaverTest@2','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
     public void onlyOneFinalField() {
        class A {
            final int b = 4;
        }

        A a = new A();

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$13A','fields':[{'this$0':'SaverTest@1'}]},{'id':'SaverTest@1','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }

    @Test
    public void oneFieldInSuperClass() {
        class B {
            int b;
        }

        class A extends B {
            int c;
        }

        A a = new A();
        a.b = 1;
        a.c = 2;

        assertSave(a, "{'objects':[{'id':'A@0','type':'apofig.saver.SaverTest$14A','fields':[{'this$0':'SaverTest@1'},{'this$0':'SaverTest@1'},{'b':'1'},{'c':'2'}]},{'id':'SaverTest@1','type':'apofig.saver.SaverTest','fields':[]}],'main':'A@0'}");
    }
}
