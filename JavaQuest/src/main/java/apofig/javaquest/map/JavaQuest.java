package apofig.javaquest.map;

import apofig.javaquest.map.object.*;
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
        Me player = new Me(map, view, initPosition.getX(), initPosition.getY(), info);
        player.setMessages(new Messages());
        player.setFactory(objects);

        players.add(player);
        map.openSpace(player);

        return player;
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
                if (!map.isNear(me.atNewPlace(), smth) && !smth.isAt(whereToGo)) {
                    smth.tryToLeave();
                }
            }
        }

        Something smthAtWay = map.getAt(whereToGo);
        smthAtWay.askMe();
        if (!smthAtWay.iCanUse()) {
            return;
        }
        smthAtWay.getBy(me.getInfo());
        me.go();
        meetWith(me);
    }

    private void meetWith(Me me) {
        for (Something object : map.getAllNear(me)) {
            object.meetWith(me);
        }

        for (Something object : map.getSomethingNear(me)) {
            object.askMe();
        }
    }

    public Something getCodeHelper(Me player) {
        for (Something smthNear : map.getSomethingNear(player)) {
            if (!smthNear.iCanLeave()) {
                return smthNear;
            }
        }
        return new Nothing();
    }

    @Override
    public void tick() {
        for (Me player : players) {
            move(player);
        }
    }

    public String printView(Me player) {
        return map.getViewArea(player);
    }

}
