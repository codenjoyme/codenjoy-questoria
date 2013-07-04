package apofig.javaquest.map;

import apofig.javaquest.map.object.*;
import apofig.javaquest.services.Tickable;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:50 PM
 */
public class JavaQuest implements Tickable {

    private TerritoryMap map;
    private ObjectFactory factory;
    private Player info;
    private Me me;

    public JavaQuest(Settings settings) {
        info = new Player();
        factory = new ObjectFactoryImpl(settings.getMonsters());
        MapLoader loader = settings.getMapLoader();
        map = new TerritoryMapImpl(loader, factory);

        newHero(settings.getViewAreaSize(), loader.getPlayerX(), loader.getPlayerY());
        map.openSpace(me);
    }

    private void newHero(int viewAreaSize, int x, int y) {
        PlayerView view = new PlayerView(viewAreaSize);
        me = new Me(map, view, x, y);
        me.setMessages(new Messages());
        me.setFactory(factory);
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
        smthAtWay.getBy(info);
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

    public String getMessage() {
        return me.getMessages().getLast(60);
    }

    public Player getPlayerInfo() {
        return info;
    }

    public Something getCodeHelper() {
        for (Something smthNear : map.getSomethingNear(me)) {
            if (!smthNear.iCanLeave()) {
                return smthNear;
            }
        }
        return new Nothing();
    }

    @Override
    public void tick() {
        move(me);
    }

    public Joystick getMe() {
        return me;
    }

    @Override
    public String toString() {
        return map.getViewArea(me);
    }
}
