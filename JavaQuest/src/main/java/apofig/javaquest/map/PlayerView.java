package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 4:07 PM
 */
public class PlayerView {

    private char[][] viewMask;
    private int size;
    private static final boolean CANT_SEE = false;
    private static final boolean CAN_SEE = true;
    private static final boolean WALL = true;
    private static final boolean EMPTY = false;

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

    private boolean canSee(int x, int y) {
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

    public void see(int posx, int posy, int mapSize, Apply see) {
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                int dx = x + posx - radius();
                int dy = y + posy - radius();
                if (dx >= 0 && dy >= 0 && dy < mapSize && dx < mapSize) {
                    if (canSee(x, y)) {
                        see.xy(dx, dy, CAN_SEE, EMPTY);
                    } else {
                        see.xy(dx, dy, CANT_SEE, false);
                    }
                } else {
                    see.xy(dx, dy, CAN_SEE, WALL);
                }
            }
        }
    }
}
