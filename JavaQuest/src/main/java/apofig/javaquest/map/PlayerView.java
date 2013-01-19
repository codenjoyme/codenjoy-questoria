package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 4:07 PM
 */
public class PlayerView {

    private char[][] viewMask;
    private int size;

    public PlayerView(int size) {
        this.size = size;
        viewMask = new char[size][size];
        buildMask();
    }

    public int radius() {
        return (size - 1)/2;
    }


    public int size() {
        return size;
    }

    public boolean canSee(int x, int y) {
        return viewMask[x][y] == ' ';
    }

    private void buildMask() {
        final String[] mask = new String[]{
                "?????????????",
                "?????   ?????",
                "???       ???",
                "??         ??",
                "??         ??",
                "?           ?",
                "?           ?",
                "?           ?",
                "??         ??",
                "??         ??",
                "???       ???",
                "?????   ?????",
                "?????????????"};

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                viewMask[x][y] = mask[y].charAt(x);
            }
        }
    }
}
