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
        boolean isOutOfMask = x < 0 || x >= size || y < 0 || y >= size;
        return !isOutOfMask && viewMask[x][y] == ' ';
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

    public void see(int cx, int cy, int ix, int iy, int mapWidth, int mapHeight, Apply see) {
        for (int dy = radius(); dy >= -radius(); dy--) {
            for (int dx = -radius(); dx <= radius(); dx++) {
                int x = dx + cx;
                int y = dy + cy;
                boolean isWall = (x < 0 || y < 0 || y >= mapHeight || x >= mapWidth);

                int ixx = radius() + x - ix;
                int iyy = radius() + y - iy;
                boolean canSee = canSee(ixx, iyy);
                see.xy(x, y, canSee, isWall);
            }
        }
    }

    public void near(int cx, int cy, int mapWidth, int mapHeight, Apply meet) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                int x = dx + cx;
                int y = dy + cy;
                boolean isWall = (x < 0 || y < 0 || y >= mapHeight || x >= mapWidth);
                meet.xy(x, y, true, isWall);
            }
        }
    }

    public int size() {
        return size;
    }
}
