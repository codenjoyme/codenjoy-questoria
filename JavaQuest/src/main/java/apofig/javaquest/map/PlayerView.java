package apofig.javaquest.map;

import org.apache.commons.lang.StringUtils;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 4:07 PM
 */
public class PlayerView {

    private static final String[] VIEW_CIRCLE = new String[]{
            "????   ????",
            "??       ??",
            "?         ?",
            "?         ?",
            "           ",
            "           ",
            "           ",
            "?         ?",
            "?         ?",
            "??       ??",
            "????   ????"};
    private char[][] viewMask;
    private int size;

    public PlayerView(int size) {
        if (size < VIEW_CIRCLE.length) {
            throw new IllegalArgumentException("View size must be more than 10!");
        }
        if (size % 2 == 0) {
            throw new IllegalArgumentException("View size must be an odd!");
        }
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
        String[] mask = new String[size];
        int delta = (size - VIEW_CIRCLE.length)/2;
        for (int y = 0; y < size; y++) {
            if (y < delta || y >= (size - delta)) {
                mask[y] = StringUtils.repeat("?", size);
            } else {
                String temp = VIEW_CIRCLE[y - delta];
                temp = StringUtils.leftPad(temp, size - delta, "?");
                temp = StringUtils.rightPad(temp, size, "?");
                mask[y] = temp;
            }
        }

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

    public void near(int posx, int posy, int mapSize, Apply meet) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int dx = x + posx;
                int dy = y + posy;
                boolean isWall = (dx < 0 || dy < 0 || dy >= mapSize || dx >= mapSize);
                meet.xy(dx, dy, true, isWall);
            }
        }
    }
}
