package apofig.saver.dummy;

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
