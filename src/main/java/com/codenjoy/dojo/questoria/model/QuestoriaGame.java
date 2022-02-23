package com.codenjoy.dojo.questoria.model;

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

import com.codenjoy.dojo.questoria.model.items.*;
import com.codenjoy.dojo.questoria.model.items.impl.Nothing;
import com.codenjoy.dojo.questoria.model.items.impl.Wall;
import com.codenjoy.dojo.questoria.model.items.impl.drone.Drone;
import com.codenjoy.dojo.questoria.model.items.monster.CodeHelper;
import com.codenjoy.dojo.questoria.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.Tickable;
import com.codenjoy.dojo.services.field.PointField;
import com.codenjoy.dojo.services.multiplayer.GameField;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.settings.SettingsReader;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.services.PointImpl.pt;

public class QuestoriaGame implements Tickable, GameField {

    private Level level;
    private Dice dice;
    private GameSettings settings;
    private PointField field;

    private FieldLoader fieldLoader;
    private HeroField heroField;
    private FieldLocator locator;
    private ObjectFactory objects;
    private List<Me> players;
    private int viewSize;
    private Point startSpot;

    private QuestoriaGame() {}

    public QuestoriaGame(Dice dice, Level level, GameSettings settings) {
        this.level = level;
        this.dice = dice;
        this.settings = settings;
        this.field = new PointField();

        clearScore();

        fieldLoader = new FieldLoaderImpl().load(level.map());
        startSpot = level.heroes().get(0).copy();
        TerritoryField field = new TerritoryField(fieldLoader);
        heroField = field;
        objects = new ObjectFactoryImpl(settings.monsters(), field);
        locator = objects.getLocator();
        players = new LinkedList<>();
        viewSize = settings.viewSize();
    }

    public void clearScore() {
        if (level == null) return;

        level.saveTo(field);
        field.init(this);

        // other clear score actions
        // super.clearScore(); // TODO implement me
    }

    @Override
    public void newGame(GamePlayer player) {
        // TODO implement me
    }

    @Override
    public void remove(GamePlayer player) {
        // TODO implement me
    }

    @Override
    public SettingsReader settings() {
        return settings;
    }

    @Override
    public int size() {
        return field.size();
    }

    public Me newPlayer(String name) {
        if (hasPlayer(name)) {
            throw new IllegalArgumentException(String.format("Игрок с именем '%s' уже зарегистрирован", name));
        }

        PlayerView view = new PlayerView(viewSize);
        PlayerInfoImpl info = new PlayerInfoImpl(name);

        Point point = findFreePosition();
        Me player = new Me(objects, heroField, locator, view, new Messages(info.getName()), point.getX(), point.getY(), info);
        objects.add(player);
        players.add(player);

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
        Point point = pt(startSpot.getX(), startSpot.getY());
        while (!(locator.getAt(point, null) instanceof Nothing)) {
            point = pt(point.getX() + 1, point.getY());
        }
        return point;
    }

    private void move(Me me) {
        Point whereToGo = me.whereToGo();
        if (whereToGo == null) {
            return;
        }

        List<Something> somethingNear = locator.getNear(me);
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
                    if (!locator.isNear(me.atNewPlace(), smth)
                            && !locator.getAt(whereToGo, me).equals(smth))
                    {
                        leaveable.tryToLeave(me);
                        if (!(smth instanceof Drone)) {  // TODO а я вот тут так буду перечислять все типы? Как-то не очень это все
                            me.leave((TalkingObject) smth);
                        }
                    }
                }
            }
        }

        Something smthAtWay = locator.getAt(whereToGo, me);
        //if (!smthAtWay.isBusy()) { // TODO implement me
            if (smthAtWay instanceof Askable) {
                ((Askable)smthAtWay).ask();
            }
        //}
        if (smthAtWay instanceof Usable) {
            ((Usable)smthAtWay).getBy(me.getInfo());
            me.go();
            meetWith(me, somethingNear);
        }
    }

    private void meetWith(Me me, List<Something> alreadyMeet) {
        List<Something> newObjects = new LinkedList<>();

        for (Something object : locator.getNear(me)) {
            if (!alreadyMeet.contains(object)) {
                newObjects.add(object);
            }
        }

        // Something object = newObjects.get(0);  // TODO а что, если встречаемся с несколькими объектами?

        for (Something object : newObjects) {
            if (object instanceof CanBeBusy) {
                CanBeBusy canBeBusy = (CanBeBusy) object;
                if (canBeBusy.isBusy()) {
                    ((TalkingObject) object).connect(me);  // TODO вот это как-то ну совсем не очень. Архитектуру Саня придумай!
                    canBeBusy.sayWhenBusy();
                    ((TalkingObject) object).disconnect(me);
                    continue;
                }
            }

            me.meetWith((TalkingObject) object);

            if (object instanceof Wall) continue;

            if (object instanceof Askable) {
                ((Askable)object).ask();
            }

            if (object instanceof Me) {
                me.ask();
            }
        }
    }

    public CodeHelper getCodeHelper(Me player) {
        for (Something smthNear : locator.getNear(player)) {
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
        return player.getView();
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
        foundPlayer.die();
    }

    protected FieldLoader field() {
        return fieldLoader;
    }
}
