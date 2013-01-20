package apofig.javaquest.map;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:13 PM
 */
public class TerritoryMap {

    private int width;
    private int height;
    private char[][] map, fog;
    private int posx, posy;
    private PlayerView view;
    private String message;

    public TerritoryMap(MapLoader loader, int viewAreaSize) {
        view = new PlayerView(viewAreaSize);

        this.width = loader.getWidth();
        this.height = loader.getHeight();
        map = loader.getMap();
        fog = loader.getFog();
        posx = -1; posy = -1;
        changePos(loader.getPlayerX(), loader.getPlayerY());
    }

    public void changePos(int x, int y) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
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
        view.see(posx, posy, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (canSee && !isWall) {
                    fog[x][y] = ' ';
                }
            }
        });
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


        result.append("╔" + StringUtils.repeat("═", view.size()*2) + "╗\n");
        view.see(posx, posy, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                boolean startLine = (posx - x) == view.radius();
                if (startLine) {
                    result.append("║");
                }

                if (isWall) {
                    if (canSee) {
                        result.append("##");
                    } else {
                        result.append("??");
                    }
                } else {
                    if (fog[x][y] == '?') {
                        result.append("??");
                    } else {
                        if (map[x][y] == '?') {
                            result.append("??");
                        }
                        result.append(map[x][y]);
                        result.append(' ');
                    }
                }

                boolean endLine = (x - posx) == view.radius();
                if (endLine) {
                    result.append("║\n");
                }
            }
        });
        result.append('╚' + StringUtils.repeat("═", view.size()*2) + '╝');

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

        view.near(posx, posy, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (!isWall && map[x][y] != ' ' && map[x][y] != '#') {
                    result.add(wrap(x, y));
                }
            }
        });

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
