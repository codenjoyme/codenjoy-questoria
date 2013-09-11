package apofig.saver.dummy;

/**
 * User: sanja
 * Date: 12.09.13
 * Time: 1:04
 */
public class ChildForIntContainer extends IntContainer {
    private int b;

    private ChildForIntContainer() {
        super(0);
    }

    public ChildForIntContainer(int a, int b) {
        super(a);
        this.b = b;
    }
}
