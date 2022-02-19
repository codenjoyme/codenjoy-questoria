package apofig.saver.dummy;

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
