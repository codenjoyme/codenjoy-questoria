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
                boolean isWall = (dx < 0 || dy < 0 || dy >= mapSize || dx >= mapSize);
                boolean canSee = canSee(x, y);
                see.xy(dx, dy, canSee, isWall);
            }
        }
    }
}
