package apofig.javaquest.map;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:13 PM
 */
public class TerritoryMap {

    private int size;
    private char[][] map, fog;
    private int posx, posy;
    private PlayerView view;
    private String message;

    public TerritoryMap(int size, int viewAreaSize) {
        view = new PlayerView(viewAreaSize);
        this.size = size;
        map = new char[size][size];
        fog = new char[size][size];

        fill(fog, '?');
        fill(map, ' ');
        map[40][22] = '@';

        posx = -1;
        posy = -1;
        changePos(20, 20);
    }

    public void changePos(int x, int y) {
        if (y < 0 || x < 0 || y >= size || x >= size) {
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
        view.see(posx, posy, size, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (canSee && !isWall) {
                    fog[x][y] = ' ';
                }
            }
        });
    }

    private void fill(char[][] m, char с) {
        for (int x = 0; x < m.length; x++) {
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

        view.see(posx, posy, size, new Apply() {
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
                result.append(" ");

                boolean endLine = (x - posx) == view.radius();
                if (endLine) {
                    result.append('\n');
                }
            }
        });

        printMessage(result);

        return result.toString();
    }

    private void printMessage(StringBuffer result) {
        if (message != null) {
            result.append('\n');
            result.append(message);
            message = null;
        }
    }

    public List<Something> getSomethingNearMe() {
        final List<Something> result = new LinkedList<Something>();

        view.near(posx, posy, size, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (!isWall && map[x][y] != ' ' && map[x][y] != '#') {
                    result.add(wrap(x, y));
                }
            }
        });

        for (Something smth : result) {
            if (smth instanceof Nothing) {
                result.remove(smth);
            }
        }

        return result;
    }

    private Something wrap(final int x, final int y) {
        char c = map[x][y];
        if (c == ' ') {
            return new Nothing();
        } else if (c == '@') {
            return new Monster(new OnKill() {
                public void doit() {
                    map[x][y] = ' ';
                }
            });
        }
        throw new UnsupportedOperationException("WTF! New object in world - " + c);
    }

    public void writeMessage(String message) {
        this.message = message;
    }
}
