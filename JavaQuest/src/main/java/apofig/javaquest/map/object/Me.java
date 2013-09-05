package apofig.javaquest.map.object;

import apofig.javaquest.map.*;
import apofig.javaquest.map.object.dron.MeetWithHero;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:57
 */
public class Me extends TalkingObject implements Viewable, Joystick, Something {

    private final World world;
    private PlayerView view;
    private int x;
    private int y;
    private Player info;
    private Point whereToGo;
    private TerritoryMap map;

    public Me(ObjectFactory objects, TerritoryMap map, PlayerView view, Messages messages, int x, int y, Player info) {
        this.map = map;
        this.view = view;
        this.x = x;
        this.y = y;
        this.info = info;

        setMessenger(new MessengerImpl());
        messenger.add(messages);

        world = new WorldImpl(objects, new MapPlace(map.getMap(), x, y), this);  // TODO тут как-то заумно очень!
        map.getMap().set(x, y, 'A');

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

    @Override
    public boolean isAt(Point point) {
        return this.x == point.getX() && this.y == point.getY();
    }

    private void tryToGo(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        whereToGo = new PointImpl(x + dx, y + dy);
    }

    public void go() {
        if (whereToGo != null) {
            world.move(whereToGo.getX(), whereToGo.getY());
            x = whereToGo.getX();
            y = whereToGo.getY();
            stop();

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
        messenger.say(message);
        if (map.getSomethingNear(this).isEmpty()) {
            Something whereIAm = map.getAt(this);
            whereIAm.answer(message);
        }

        for (Something smthNear : map.getSomethingNear(this)) {
            smthNear.answer(message);
        }
    }

    @Override
    public PlayerView view() {
        return view;
    }

    public Viewable atNewPlace() {
        return new Viewable() {
            @Override
            public PlayerView view() {
                return view;
            }

            @Override
            public int getY() {
                return whereToGo.getY();
            }

            @Override
            public int getX() {
                return whereToGo.getX();
            }

            @Override
            public boolean isAt(Point point) {
                return whereToGo.isAt(point);
            }
        };
    }

    public Player getInfo() {
        return info;
    }

    @Override
    public int hashCode() {
        return info.getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Me)) {
            return false;
        }

        Me me = (Me)o;

        return me.getInfo().getName().equals(info.getName());
    }

    public void stop() {
        whereToGo = null;
    }

    @Override
    public void answer(String message) {

    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Привет, я такой же как и ты игрок!");
    }

    @Override
    public boolean iCanUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return world.make(' ');
    }

    @Override
    public char symbol() {
        return 'A';
    }

    @Override
    public void getBy(Player info) {

    }

    @Override
    public void tryToLeave() {
        messenger.say("Ну пока!");
    }

    @Override
    public String getCode() {
        return "";
    }

    public World getWorld() {
        return world;
    }

    public void meetWith(Something object) {
        TalkingObject talking = (TalkingObject) object;
        talking.getMessenger().add(messenger.getMessages());  // TODO выделить это в мессенджер

        if (object instanceof Me) {
            messenger.add(talking.getMessenger().getMessages());
        }

        if (object instanceof MeetWithHero) {
            ((MeetWithHero) object).meetWith(this);
        }
    }

    public void leave(Something object) {
        TalkingObject talking = (TalkingObject) object;
        talking.getMessenger().remove(messenger.getMessages());   // TODO выделить это в мессенджер

        if (object instanceof Me) {
            messenger.remove(talking.getMessenger().getMessages());
        }

        if (object instanceof MeetWithHero) {
            ((MeetWithHero) object).leave(this);
        }
    }
}