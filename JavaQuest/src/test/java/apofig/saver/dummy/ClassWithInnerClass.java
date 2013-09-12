package apofig.saver.dummy;

/**
 * User: sanja
 * Date: 12.09.13
 * Time: 2:28
 */
public class ClassWithInnerClass {
    public Inner a;

    private ClassWithInnerClass() {}

    public ClassWithInnerClass(Inner a) {
        this.a = a;
    }

    public class Inner {
        int b;

        public Inner() {}

        public Inner(int b) {
            this.b = b;
        }
    }

}
