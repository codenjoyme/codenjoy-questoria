package apofig.saver.dummy;

/**
 * User: sanja
 * Date: 19.09.13
 * Time: 20:27
 */
public class ClassWithAnnonymClass {

    private Runnable r;

    public ClassWithAnnonymClass() {}

    public ClassWithAnnonymClass(Runnable runnable) {
        r = runnable;
    }
}
