package apofig.saver.dummy;

public class ArrayOfArrayOfCharContainer {
    private char[][] array;

    private ArrayOfArrayOfCharContainer() {}

    public ArrayOfArrayOfCharContainer(int i) {
        array = new char[][] { new char[]{ 'a', 'b', 'c' }, new char[]{ 'q', 'w', 'e' }};
    }
}
