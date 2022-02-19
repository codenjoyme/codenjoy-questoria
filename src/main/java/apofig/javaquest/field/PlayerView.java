package apofig.javaquest.field;

import org.apache.commons.lang.StringUtils;

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
    
    private char[][] mask;
    
    private int size;
    private int vx = Integer.MIN_VALUE;
    private int vy;

    private PlayerView() {}

    public PlayerView(int size) {
        if (size < VIEW_CIRCLE.length) {
            throw new IllegalArgumentException("View size must be more than 10!");
        }
        if (size % 2 == 0) {
            throw new IllegalArgumentException("View size must be an odd!");
        }
        this.size = size;
        mask = new char[size][size];
        buildMask();
    }

    public int radius() {
        return (size - 1)/2;
    }

    private boolean canSee(int x, int y) {
        boolean isOutOfMask = x < 0 || x >= size || y < 0 || y >= size;
        return !isOutOfMask && mask[x][y] == ' ';
    }

    public void moveMeTo(Point point) {
        if (vx == Integer.MIN_VALUE) {
            vx = point.getX() - radius();
            vy = point.getY() - radius();
        }
        int dx = vx - point.getX();
        int adx = Math.abs(dx);
        if (adx < radius()/2) {
            vx += dx / adx;
        }

        int dy = vy - point.getY();
        int ady = Math.abs(dy);
        if (ady < radius()/2) {
            vy += dy / ady;
        }

        dx = vx + radius()*2 - point.getX();
        adx = Math.abs(dx);
        if (adx < radius()/2) {
            vx += dx / adx;
        }

        dy = vy + radius()*2 - point.getY();
        ady = Math.abs(dy);
        if (ady < radius()/2) {
            vy += dy / ady;
        }
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
                this.mask[x][y] = mask[y].charAt(x);
            }
        }
    }

    public void see(Point point, int fieldWidth, int fieldHeight, Apply see) {
        int cx = vx + radius();
        int cy = vy + radius();

        for (int dy = radius(); dy >= -radius(); dy--) {
            for (int dx = -radius(); dx <= radius(); dx++) {
                int x = dx + cx;
                int y = dy + cy;
                boolean isWall = (x < 0 || y < 0 || y >= fieldHeight || x >= fieldWidth);

                int ixx = radius() + x - point.getX();
                int iyy = radius() + y - point.getY();
                boolean canSee = canSee(ixx, iyy);
                see.xy(x, y, canSee, isWall);
            }
        }
    }

    public int size() {
        return size;
    }

    public int getX() {
        return vx;
    }
}
