package apofig.javaquest.map.object;

import apofig.javaquest.map.*;

/**
 * User: sanja
 * Date: 02.07.13
 * Time: 22:57
 */
public class Me extends TalkingObject implements Viewable, Joystick, Something, Leaveable {

    private World world;
    private MapLocator locator;
    private PlayerView view;
    private int x;
    private int y;
    private Player info;
    private Point whereToGo;
    private HeroMap map;

    private Me() {}

    public Me(ObjectFactory objects, HeroMap map, MapLocator locator, PlayerView view, Messages messages, int x, int y, Player info) {
        this.map = map;
        this.locator = locator;
        this.view = view;
        this.x = x;
        this.y = y;
        this.info = info;

        setMessenger(new MessengerImpl());
        messenger.add(messages);

        MapPlace heroPlace = map.newHero(this);
        world = new WorldImpl(objects, heroPlace, this, this);  // TODO тут как-то заумно очень!

        tryToGo(0, 0);
        view.moveMeTo(this);
        map.openSpace(this);
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
        if ("".equals(message)) {
            message = "ok";
        }
        messenger.say(message);
        for (Something smthNear : locator.getNear(this)) {
            smthNear.answer(message);
        }
    }

    @Override
    public PlayerView view() {
        return view;
    }

    public void die() {
        map.removeHero(this);
    }

    public String getView() {
        return map.getViewArea(this);
    }

    public class DummyMe extends Me  {
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

        @Override
        public String getName() {
            return info.getName();
        }

        public Me getRealMe() {
            return Me.this;
        }
    }

    public Viewable atNewPlace() {
        return new DummyMe();
    }

    public Player getInfo() {
        return info;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // так надо! Объектов не будет несколько одинаковых в системе
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o); // так надо! Объектов не будет несколько одинаковых в системе
    }

    @Override
    public String toString() {
        return String.format("Player: '%s' in place: '%s'", info, world);
    }

    public void stop() {
        whereToGo = null;
    }

    @Override
    public void answer(String message) {

    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Привет, я такой же как и ты игрок!");
    }

    @Override
    public boolean canUse() {
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
    public void tryToLeave(Me hero) {
        messenger.say("Ну пока!");
    }

    public World getWorld() {
        return world;
    }

    public void meetWith(TalkingObject object) {
        object.connect(this);

        if (object instanceof Me) {
            connect(object);
        }

        if (object instanceof MeetWithHero) {
            ((MeetWithHero) object).meetWith(this);
        }
    }

    public void leave(TalkingObject object) {
        object.disconnect(this);

        if (object instanceof Me) {
            disconnect(object);
        }

        if (object instanceof MeetWithHero) {
            ((MeetWithHero) object).leave(this);
        }
    }
}