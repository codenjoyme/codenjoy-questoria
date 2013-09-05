package apofig.javaquest.map;

import apofig.javaquest.map.object.*;
import apofig.javaquest.map.object.dron.Dron;
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

    public JavaQuest(Settings settings) {
        objects = new ObjectFactoryImpl(settings.monsters());
        MapLoader loader = settings.mapLoader();
        map = new TerritoryMapImpl(loader, objects);
        players = new LinkedList<Me>();
        viewSize = settings.viewSize();
        initPosition = settings.mapLoader().initPosition();
    }

    public Me newPlayer(String name) {
        PlayerView view = new PlayerView(viewSize);
        Player info = new Player(name);

        Point point = findFreePosition();
        Me player = new Me(objects, map, view, new Messages(), point.getX(), point.getY(), info);

        players.add(player);
        map.openSpace(player);

        return player;
    }

    private Point findFreePosition() {
        Point point = new PointImpl(initPosition.getX(), initPosition.getY());
        while (!(map.getAt(point) instanceof Nothing)) {
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

        for (Something smthNear : map.getSomethingNear(me)) {
            if (!smthNear.iCanLeave()) {
                smthNear.tryToLeave();
                return;
            }
        }

        for (Something smth : map.getSomethingNear(me)) {
            if (smth.iCanLeave()) {
                if (!map.isNear(me.atNewPlace(), smth) && !objects.isAt(smth, whereToGo)) {
                    smth.tryToLeave();
                    if (smth instanceof Me) {   // TODO testme
                        me.leave((TalkingObject) smth);
                    }
                }
            }
        }

        Something smthAtWay = map.getAt(whereToGo);
        smthAtWay.ask();
        if (smthAtWay.iCanUse()) {
            smthAtWay.getBy(me.getInfo());
            me.go();
            meetWith(me);
        }
    }

    private void meetWith(Me me) {
        for (Something object : map.getAllNear(me)) {
            me.meetWith((TalkingObject) object);
        }

        for (Something object : map.getSomethingNear(me)) {
            object.ask();
            if (object instanceof Me) {
                me.ask();
            }
        }
    }

    public Something getCodeHelper(Me player) {
        for (Something smthNear : map.getSomethingNear(player)) {
            if (!smthNear.iCanLeave() || smthNear instanceof Dron) { // TODO instanceof Dron - не ок, архитектуру надобно менять
                return smthNear;
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

}
