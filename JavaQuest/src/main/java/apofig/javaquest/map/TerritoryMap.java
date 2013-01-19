package apofig.javaquest.map;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:13 PM
 */
public class TerritoryMap {

    private static final int SIZE = 100;
    private static final int VIEW_RADIUS = 13;

    private char[][] map = new char[SIZE][SIZE];
    private char[][] viewMask = new char[VIEW_RADIUS][VIEW_RADIUS];
    private int posx, posy;

    public TerritoryMap() {
        fill(map, '?');
        buildMask();
        changePos(20, 20);
    }

    private int delta() {
        return (VIEW_RADIUS - 1)/2;
    }

    public void changePos(int x, int y) {
        removeMe();
        posx = x;
        posy = y;
        openSpace();
        setMe();
    }

    private void setMe() {
        map[posx][posy] = 'I';
    }

    private void removeMe() {
        map[posx][posy] = ' ';
    }

    private void openSpace() {
        for (int x = 0; x < VIEW_RADIUS; x++) {
            for (int y = 0; y < VIEW_RADIUS; y++) {
                if (viewMask[x][y] == ' ') {
                    map[x + posx - delta()][y + posy - delta()] = ' ';
                }
            }
        }
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

        for (int y = 0; y < VIEW_RADIUS; y++) {
            for (int x = 0; x < VIEW_RADIUS; x++) {
                viewMask[x][y] = mask[y].charAt(x);
            }
        }
    }

    private void fill(char[][] m, char с) {
        for (int x = 0; x < SIZE; x++) {
            Arrays.fill(m[x], с);
        }
    }

    public void printNearMe(OutputStream out) {
        try {
            out.write(getViewArea().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getX() {
        return posx;
    }

    public int getY() {
        return posy;
    }

    public String getViewArea() {
        StringBuffer result = new StringBuffer();

        for (int y = 0; y < VIEW_RADIUS; y++) {
            for (int x = 0; x < VIEW_RADIUS; x++) {
                result.append(map[x + posx - delta()][y + posy - delta()]);
            }
            result.append('\n');
        }



        return result.toString();
    }
}
