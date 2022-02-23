package com.codenjoy.dojo.questoria.model.items;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.questoria.client.Element;
import com.codenjoy.dojo.questoria.model.*;
import com.codenjoy.dojo.questoria.model.items.impl.Nothing;
import com.codenjoy.dojo.questoria.model.items.monster.Monster;
import com.codenjoy.dojo.services.Point;

public class Me extends TalkingObject implements Viewable, Joystick, Something, Leaveable {

    private World world;
    private FieldLocator locator;
    private PlayerView view;
    private int x;
    private int y;
    private PlayerInfoImpl info;
    private Point whereToGo;
    private HeroField filed;
    private Portal portal;

    private Me() {}

    public Me(ObjectFactory objects, HeroField filed, FieldLocator locator, PlayerView view, Messages messages, int x, int y, PlayerInfoImpl info) {
        this.filed = filed;
        this.locator = locator;
        this.view = view;
        this.x = x;
        this.y = y;
        this.info = info;

        setMessenger(new MessengerImpl());
        messenger.add(messages);

        FieldPlace heroPlace = filed.newHero(this);
        world = new WorldImpl(objects, heroPlace, this, this);

        tryToGo(0, 0);
        view.moveMeTo(this);
        filed.openSpace(this);
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
    public boolean itsMe(Point point) {
        return this.x == point.getX() && this.y == point.getY();
    }

    private void tryToGo(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        whereToGo = pt(x + dx, y + dy);
    }

    public void go() {
        if (whereToGo != null) {
            world.move(whereToGo.getX(), whereToGo.getY());
            x = whereToGo.getX();
            y = whereToGo.getY();
            stop();

            filed.openSpace(this);
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
        if (!"".equals(message)) {
            messenger.say(message);
        }
        for (Something smthNear : locator.getNear(this)) {
            if (smthNear instanceof Askable) {
                ((Askable)smthNear).answer(message);
            }
        }
    }

    @Override
    public PlayerView view() {
        return view;
    }

    public void die() {
        filed.removeHero(this);
    }

    public String getView() {
        return filed.getViewArea(this);
    }

    public int filchGold(int amount) {
        return info.filchGold(amount);
    }

    public void openPortal(Monster monster) {
        this.portal = new Portal(this, monster);
        info.setPortalCode(portal.getHash());
    }

    public void closePortal(Monster monster) {
        this.portal = null;
        info.setPortalCode(null);
        // TODO а что если у нас порталов несколько?
    }

    public Portal getPortal() {
        return portal;
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
        public boolean itsMe(Point point) {
            return whereToGo.itsMe(point);
        }

        @Override
        public String getName() {
            return info.getName();
        }

        public Me getRealMe() {
            return Me.this;
        }
    }

    public Me atNewPlace() {
        return new DummyMe();
    }

    public PlayerInfoImpl getInfo() {
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
    public Something leaveAfter() {
        return new Nothing();
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

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.OTHER_HERO;
    }
}