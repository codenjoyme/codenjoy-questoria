package apofig.javaquest.map;

import apofig.javaquest.map.object.MapPlace;
import apofig.javaquest.map.object.ObjectFactory;
import apofig.javaquest.map.object.Something;
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
public class TerritoryMapImpl implements TerritoryMap {

    // ✉ ✍ ✎ ✓ ☑ ☒ ✗ ⊕ ⊗ ☞ ☜ ♫ ✄ ✁ ∞ ♨ ☢ ✈ ☰ ☷ ♥ ★ ☆ ☺ ☹
    // ♔ ♕ ♖ ♘ ♆ ✠ ♂ ♀ ♠ ♣ ♥ ♦ ☣ ☮ ☃ ☂ ☯ ☠
    // TODO use it

    private int width;
    private int height;
    protected char[][] map;
    private char[][] fog;
    private int posx, posy;
    private PlayerView view;
    private ObjectFactory factory;

    public TerritoryMapImpl(MapLoader loader, int viewAreaSize, ObjectFactory factory) {
        this.factory = factory;
        view = new PlayerView(viewAreaSize);

        this.width = loader.getWidth();
        this.height = loader.getHeight();
        map = loader.getMap();
        fog = loader.getFog();
        posx = -1; posy = -1;
        changePos(loader.getPlayerX(), loader.getPlayerY());
    }

    @Override
    public void changePos(int x, int y) {
        if (isOutOfWorld(x, y)) {
            return;
        }
        removeMe();
        posx = x;
        posy = y;
        openSpace();
        setMe();
    }

    private boolean isOutOfWorld(int x, int y) {
        return y < 0 || x < 0 || y >= height || x >= width;
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

    @Override
    public int getX() {
        return posx;
    }

    @Override
    public int getY() {
        return posy;
    }

    @Override
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

        return result.toString();
    }

    @Override
    public List<Something> getSomethingNearMe() {
        final List<Something> result = new LinkedList<Something>();

        view.near(posx, posy, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (!isWall && map[x][y] != ' ' && map[x][y] != '#') {
                    result.add(getAt(x, y));
                }
            }
        });

        return result;
    }

    @Override
    public Something getAt(int x, int y) {
        if (isOutOfWorld(x, y)) {
            return factory.make('#', null);
        }

        return factory.make(map[x][y], new MapPlace(map, x, y));
    }

    @Override
    public boolean isNear(int xx, int yy, final Something object) {
        final boolean[] result = {false};

        view.near(xx, yy, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (!isWall && map[x][y] == object.symbol()) {
                    result[0] = true;
                }
            }
        });

        return result[0];
    }
}
