package apofig.javaquest.map;

import apofig.javaquest.map.object.MapPlace;
import apofig.javaquest.map.object.Me;
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
    private PlayerView view;
    private ObjectFactory factory;
    private Me me;

    public TerritoryMapImpl(MapLoader loader, int viewAreaSize, ObjectFactory factory) {
        this.factory = factory;
        view = new PlayerView(viewAreaSize);

        this.width = loader.getWidth();
        this.height = loader.getHeight();
        map = loader.getMap();
        fog = loader.getFog();
        me = new Me(this);
        me.moveTo(loader.getPlayerX(), loader.getPlayerY());
    }

    public boolean isOutOfWorld(int x, int y) {
        return y < 0 || x < 0 || y >= height || x >= width;
    }

    @Override
    public void openSpace(int x, int y) {
        view.moveMeTo(x, y);

        view.see(x, y, width, height, new Apply() {
            @Override
            public void xy(int xx, int yy, boolean canSee, boolean isWall) {
                if (canSee && !isWall) {
                    fog[xx][yy] = ' ';
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
    public Me me() {
        return me;
    }

    @Override
    public String getViewArea() {
        final StringBuffer result = new StringBuffer();

        result.append("╔" + StringUtils.repeat("═", view.size()*2) + "╗\n");
        view.see(me().getX(), me().getY(), width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                boolean startLine = x == view.getViewArea().x;
                if (startLine) {
                    result.append("║");
                }

                if (isWall && canSee) {
                    result.append("##");
                } else if (isWall && !canSee) {
                    result.append("??");
                } else if (fog[x][y] == '?' || map[x][y] == '?') {
                    result.append("??");
                } else if (me.getX() == x && me.getY() == y) {
                    result.append("I ");
                } else {
                    result.append(map[x][y]).append(' ');
                }

                boolean endLine = x == view.getViewArea().x + view.size() - 1;
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

        view.near(me.getX(), me.getY(), width, height, new Apply() {
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
