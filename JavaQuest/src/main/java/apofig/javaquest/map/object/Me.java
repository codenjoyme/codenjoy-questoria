package apofig.javaquest.map.object;

import apofig.javaquest.map.*;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:57
 */
public class Me extends TalkingObject implements Point, Joystick {

    private PlayerView view;
    private int x;
    private int y;
    private Player info;
    private Point whereToGo;

    private TerritoryMap map;

    public Me(TerritoryMap map, PlayerView view, int x, int y, Player info) {
        this.map = map;
        this.view = view;
        this.x = x;
        this.y = y;
        this.info = info;
        tryToGo(0, 0);
        view.moveMeTo(this);
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    private void tryToGo(int dx, int dy) {
        whereToGo = new PointImpl(x + dx, y + dy);
    }

    public void go() {
        if (whereToGo != null) {
            x = whereToGo.getX();
            y = whereToGo.getY();
            whereToGo = null;

            map.openSpace(this);
        }
    }

    public Point whereToGo() {
        return whereToGo;
    }

    @Override
    public void moveRight() {
        tryToGo(1, 0);
    }

    @Override
    public void moveLeft() {
        tryToGo(-1, 0);
    }

    @Override
    public void moveUp() {
        tryToGo(0, 1);
    }

    @Override
    public void moveDown() {
        tryToGo(0, -1);
    }

    @Override
    public String getName() {
        return info.getName();
    }

    @Override
    public void attack(String message) {
        say(message);
        if (map.getSomethingNear(this).isEmpty()) {
            Something whereIAm = map.getAt(this);
            whereIAm.answer(message);
        }

        for (Something smthNear : map.getSomethingNear(this)) {
            smthNear.answer(message);
        }
    }

    public PlayerView view() {
        return view;
    }

    public Me atNewPlace() {    // TODO cheat
        return new Me(map, view, whereToGo.getX(), whereToGo.getY(), info);
    }

    public Player getInfo() {
        return info;
    }
}
