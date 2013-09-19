package apofig.javaquest.map;

import apofig.javaquest.map.object.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

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
    private Map map;
    private java.util.Map<Viewable, Map> fogs;
    private ObjectFactory objects;

    private TerritoryMapImpl() {}

    public TerritoryMapImpl(MapLoader loader, ObjectFactory objects) {
        this.objects = objects;
        width = loader.width();
        height = loader.height();
        map = loader.map();
        fogs = new HashMap<Viewable, Map>();
    }

    @Override
    public void openSpace(Viewable me) {
        final Map fog = fog(me);

        me.view().moveMeTo(me);  // TODO подумать над этим

        me.view().see(me, width, height, new Apply() {
            @Override
            public void xy(int xx, int yy, boolean canSee, boolean isWall) {
                if (canSee && !isWall) {
                    fog.set(xx, yy, ' ');
                }
            }
        });
    }

    private Map fog(Viewable me) {
        if (!fogs.containsKey(me)) {
            fogs.put(me, new Map(width, height, '?'));
        }
        return fogs.get(me);
    }

    public void printNear(Me me, OutputStream out) {
        try {
            out.write(getViewArea(me).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getViewArea(final Viewable me) {
        final StringBuffer result = new StringBuffer();

        result.append("╔" + StringUtils.repeat("═", me.view().size()*2) + "╗\n");
        me.view().see(me, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                Point pt = new PointImpl(x, y);
                boolean startLine = x == me.view().getX();
                if (startLine) {
                    result.append("║");
                }

                if (isWall && canSee) {
                    result.append("##");
                } else if (isWall && !canSee) {
                    result.append("??");
                } else if (fog(me).get(x, y) == '?' || map.get(x, y) == '?') {
                    result.append("??");
                } else if (playerAt(pt)) {
                    if (me.isAt(pt)) {
                        result.append("I ");
                    } else {
                        result.append("A ");
                    }
                } else {
                    result.append(map.get(x, y)).append(' ');
                }

                boolean endLine = x == me.view().getX() + me.view().size() - 1;
                if (endLine) {
                    result.append("║\n");
                }
            }
        });
        result.append('╚' + StringUtils.repeat("═", me.view().size()*2) + '╝');

        return result.toString();
    }

    private boolean playerAt(Point point) {
        for (Viewable player : fogs.keySet()) {
            if (player.isAt(point)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Something> getSomethingNear(final Viewable me) {    // TODO вот этот метод со следующим объединить...
        final List<Something> result = new LinkedList<Something>();

        me.view().near(me, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (!isWall && map.get(x, y) != ' ' && map.get(x, y) != '#') {
                    result.add(getAt(new PointImpl(x, y)));
                }
            }
        });

        return result;
    }


    @Override
    public List<Something> getAllNear(final Viewable me) {
        final List<Something> result = new LinkedList<Something>();

        me.view().near(me, width, height, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (!isWall && map.get(x, y) == ' ') {
                    return;
                }

                result.add(getAt(new PointImpl(x, y)));
            }
        });

        return result;
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public void remove(Viewable me) {    // TODO test me
        map.set(me.getX(), me.getY(), ' ');
        fogs.remove(me);
    }

    @Override
    public Something getAt(Point point) {
        return objects.get(map.get(point));
    }

    @Override
    public boolean isNear(Viewable me, final Something object) {
        return getAllNear(me).contains(object);
    }
}
