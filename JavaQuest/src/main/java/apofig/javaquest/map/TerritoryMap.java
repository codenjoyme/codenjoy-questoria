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
    private char[][] fog = new char[SIZE][SIZE];
    private int posx = -1, posy = -1;
    private PlayerView view;

    public TerritoryMap() {
        view = new PlayerView(13);
        fill(fog, '?');
        fill(map, ' ');
        map[40][20] = '@';
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
        view.see(posx, posy, SIZE, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (canSee && !isWall) {
                    fog[x][y] = ' ';
                }
            }
        });
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
        final StringBuffer result = new StringBuffer();

        view.see(posx, posy, SIZE, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (isWall) {
                    if (canSee) {
                        result.append('#');
                    } else {
                        result.append('?');
                    }
                } else {
                    if (fog[x][y] == '?') {
                        result.append('?');
                    } else {
                        result.append(map[x][y]);
                    }
                }

                boolean endLine = (x - posx) == view.radius();
                if (endLine) {
                    result.append('\n');
                }
            }
        });

        return result.toString();
    }
}
