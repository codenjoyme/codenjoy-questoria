package apofig.saver.dummy;

/**
 * User: sanja
 * Date: 12.09.13
 * Time: 2:28
 */
public class ClassWithStaticInnerClass {
    public Inner a;

    private ClassWithStaticInnerClass() {}

    public ClassWithStaticInnerClass(Inner a) {
        this.a = a;
    }

    public static class Inner {
        int b;

        private Inner() {}

        public Inner(int b) {
            this.b = b;
        }
    }

}
