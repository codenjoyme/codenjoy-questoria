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
    private char[][] map = new char[SIZE][SIZE];
    private int posx = -1, posy = -1;
    private PlayerView view;

    public TerritoryMap() {
        view = new PlayerView(13);
        fill(map, '?');
        changePos(20, 20);
    }

    public void changePos(int x, int y) {
        if (y < 0 || x < 0 || y >= SIZE || x >= SIZE) {
            return;
        }
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
        if (posx != -1 || posy != -1) {
            map[posx][posy] = ' ';
        }
    }

    private void openSpace() {
        for (int x = 0; x < view.size(); x++) {
            for (int y = 0; y < view.size(); y++) {
                if (view.canSee(x, y)) {
                    int dx = x + posx - view.radius();
                    int dy = y + posy - view.radius();
                    if (dx >= 0 && dy >= 0 && dy < SIZE && dx < SIZE) {
                        map[dx][dy] = ' ';
                    }
                }
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

        for (int y = view.size() - 1; y >= 0; y--) {
            for (int x = 0; x < view.size(); x++) {
                int dx = x + posx - view.radius();
                int dy = y + posy - view.radius();
                if (dx < 0 || dy < 0 || dx >= SIZE || dy >= SIZE) {
                    result.append('#');
                } else {
                    result.append(map[dx][dy]);
                }
            }
            result.append('\n');
        }

        return result.toString();
    }
}
