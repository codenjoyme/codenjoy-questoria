package apofig.saver.dummy;

public class ClassWithAnnonymClass {

    private Runnable r;

    public ClassWithAnnonymClass() {}

    public ClassWithAnnonymClass(Runnable runnable) {
        r = runnable;
    }
}
