package apofig.saver.dummy;

public class ClassWithInnerClass {
    public Inner a;

    private ClassWithInnerClass() {}

    public ClassWithInnerClass(Inner a) {
        this.a = a;
    }

    public class Inner {
        int b;

        private Inner() {}

        public Inner(int b) {
            this.b = b;
        }
    }

}
