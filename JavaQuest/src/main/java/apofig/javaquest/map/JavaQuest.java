package apofig.javaquest.map;

import apofig.javaquest.map.object.*;
import apofig.javaquest.map.object.impl.Nothing;
import apofig.javaquest.map.object.impl.Wall;
import apofig.javaquest.map.object.monster.CodeHelper;
import apofig.javaquest.services.Tickable;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:50 PM
 */
public class JavaQuest implements Tickable {

    private TerritoryMap map;
    private ObjectFactory objects;
    private List<Me> players;
    private int viewSize;
    private Point initPosition;

    private JavaQuest() {}

    public JavaQuest(Settings settings) {
        objects = new ObjectFactoryImpl(settings.monsters());
        MapLoader loader = settings.mapLoader();
        map = new TerritoryMapImpl(loader, objects);
        players = new LinkedList<Me>();
        viewSize = settings.viewSize();
        initPosition = settings.mapLoader().initPosition();
    }

    public Me newPlayer(String name) {
        if (hasPlayer(name)) {
            throw new IllegalArgumentException(String.format("Игрок с именем '%s' уже зарегистрирован", name));
        }

        PlayerView view = new PlayerView(viewSize);
        Player info = new Player(name);

        Point point = findFreePosition();
        Me player = new Me(objects, map, view, new Messages(), point.getX(), point.getY(), info);
        objects.add(player);

        players.add(player);
        map.init(player);
        map.openSpace(player);

        return player;
    }

    private boolean hasPlayer(String name) {
        for (Me player : players) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private Point findFreePosition() {
        Point point = new PointImpl(initPosition.getX(), initPosition.getY());
        while (!(map.getAt(point, null) instanceof Nothing)) {
            point = new PointImpl(point.getX() + 1, point.getY());
        }
        return point;
    }

    public TerritoryMap getTerritoryMap() {
        return map;
    }

    private void move(Me me) {
        Point whereToGo = me.whereToGo();
        if (whereToGo == null) {
            return;
        }

        List<Something> somethingNear = map.getNear(me);
        for (Something smth : somethingNear) {
            if (smth instanceof Leaveable) {
                Leaveable leaveable = (Leaveable) smth;
                if (!leaveable.canLeave(me)) {
                    leaveable.tryToLeave(me);
                    return;
                }
            }
        }

        for (Something smth : somethingNear) {
            if (smth instanceof Leaveable) {
                Leaveable leaveable = (Leaveable)smth;
                if (leaveable.canLeave(me)) {
                    if (!map.isNear(me.atNewPlace(), smth) && !objects.isAt(smth, whereToGo)) {
                        leaveable.tryToLeave(me);
                        if (smth instanceof Me) {
                            me.leave((TalkingObject) smth);
                        }
                    }
                }
            }
        }

        Something smthAtWay = map.getAt(whereToGo, me);
        //if (!smthAtWay.isBusy()) { // TODO implement me
            smthAtWay.ask();
        //}
        if (smthAtWay.canUse()) {
            smthAtWay.getBy(me.getInfo());
            me.go();
            meetWith(me, somethingNear);
        }
    }

    private void meetWith(Me me, List<Something> alreadyMeet) {
        List<Something> newObjects = new LinkedList<>();

        for (Something object : map.getNear(me)) {
            if (!alreadyMeet.contains(object)) {
                newObjects.add(object);
            }
        }

        for (Something object : newObjects) {
            if (object instanceof CanBeBusy) {
                CanBeBusy canBeBusy = (CanBeBusy) object;
                if (canBeBusy.isBusy()) {
                    ((TalkingObject) object).connect(me);
                    canBeBusy.sayWhenBusy();
                    ((TalkingObject) object).disconnect(me);
                    continue;
                }
            }

            me.meetWith((TalkingObject) object);

            if (object instanceof Wall) continue;

            object.ask();
            if (object instanceof Me) {
                me.ask();
            }
        }
    }

    public CodeHelper getCodeHelper(Me player) {
        for (Something smthNear : map.getNear(player)) {
            if (smthNear instanceof CodeHelper) {
                return (CodeHelper) smthNear;
            }
        }
        return new Nothing();
    }

    @Override
    public void tick() {
        for (Me player : players) {
            move(player);
            player.stop();
        }
        objects.tick();
    }

    public String printView(Me player) {
        return map.getViewArea(player);
    }

    public void remove(String name) {
        Me foundPlayer = null;
        for (Me player : players) {
            if (player.getName().equals(name)) {
                foundPlayer = player;
                break;
            }
        }

        if (foundPlayer == null) {
            throw new IllegalArgumentException(String.format("Игрок с именем '%s' не найден", name));
        }

        players.remove(foundPlayer);
        objects.remove(foundPlayer);
        map.remove(foundPlayer);
    }
}
