package apofig.saver.dummy;

/**
 * User: sanja
 * Date: 11.09.13
 * Time: 21:32
 */
public class ArrayOfArrayOfCharContainer {
    private char[][] array;

    private ArrayOfArrayOfCharContainer() {}

    public ArrayOfArrayOfCharContainer(int i) {
        array = new char[][] { new char[]{ 'a', 'b', 'c' }, new char[]{ 'q', 'w', 'e' }};
    }
}
