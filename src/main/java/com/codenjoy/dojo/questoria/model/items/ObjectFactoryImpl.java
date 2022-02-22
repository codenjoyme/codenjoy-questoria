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

import com.codenjoy.dojo.questoria.model.Dieble;
import com.codenjoy.dojo.questoria.model.Locator;
import com.codenjoy.dojo.questoria.model.TerritoryField;
import com.codenjoy.dojo.questoria.model.items.impl.Nothing;
import com.codenjoy.dojo.questoria.model.items.monster.MonsterFactory;
import com.codenjoy.dojo.questoria.model.items.monster.MonsterPool;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.Tickable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.codenjoy.dojo.questoria.client.Element.*;

public class ObjectFactoryImpl implements ObjectFactory {
    // TODO мне кажется эта штука должна быть для каждого юзера отдельной, покуда монстры и комни предткновения у каждого юзера свои...

    private Locator locator;
    private ObjectLoader loader;
    private Map<String, MonsterPool> monsters;
    private Map<Something, World> objects;
    private MonsterFactory monstersFactory;

    private ObjectFactoryImpl() {}

    public ObjectFactoryImpl(MonsterFactory factory, TerritoryField field) {
        monstersFactory = factory;
        locator = new Locator(field, this);
        monsters = new HashMap<>();
        objects = new HashMap<>();
        loader = new ObjectLoader();
    }

    @Override
    public Locator getLocator() {
        return locator;
    }

    @Override
    public Something get(Place place, Me founder, Object params) {
        if (founder instanceof Me.DummyMe) {
            founder = ((Me.DummyMe)founder).getRealMe();
        }

        Messenger messenger = null;

        for (Something smth : getObjects()) {
            if (isAt(smth, place)) {
                if (smth.symbol() != place.getChar()) {
                    killSomething(smth);
                    messenger = ((TalkingObject)smth).getMessenger();
                } else {
                    return smth;
                }
            }
        }

        return initObject(place, messenger, founder, params);
    }

    public Collection<Something> getObjects() {
        return new LinkedList<>(objects.keySet());
    }

    private boolean isAt(Something smth, Point place) {
        if (smth instanceof Me) {
            return ((Me)smth).itsMe(place);
        }
        return objects.get(smth).place().itsMe(place);
    }

    @Override
    public void remove(Me me) {
        objects.remove(me);
        monsters.remove(me.getName());
    }

    private Something initObject(Place place, Messenger messenger, Me founder, Object params) {
        Something result = newObject(place.getChar(), founder);

        WorldImpl world = new WorldImpl(this, place, result, founder);

        if (result instanceof Nothing) {
            return result;
        }

        objects.put(result, world);

        if (messenger == null) {
            messenger = new MessengerImpl();
        }
        ((SetMessenger)result).setMessenger(messenger);

        if (SetPlace.class.isAssignableFrom(result.getClass())) {
            ((SetPlace)result).setPlace(place);
        }

        if (SetLocator.class.isAssignableFrom(result.getClass())) {
            ((SetLocator)result).setLocator(locator);
        }

        if (SetWorld.class.isAssignableFrom(result.getClass())) {
            ((SetWorld)result).setWorld(world);
        }

        if (SetParameters.class.isAssignableFrom(result.getClass())) {
            ((SetParameters)result).setParameters(params);
        }

        return result;
    }

    private void killSomething(Something smth) {
        objects.remove(smth);
        if (Dieble.class.isAssignableFrom(smth.getClass())) {
            ((Dieble)smth).die();
        }
    }

    @Override
    public void add(Me me) {
        objects.put(me, me.getWorld());
        monsters.put(me.getName(), monstersFactory.newMonsters());
    }

    private Something newObject(char c, Me founder) {
        if (c == NOTHING.ch() || c == HERO.ch()) {
            return new Nothing();
        } else if (c == OTHER_HERO.ch()) {
            throw new IllegalStateException("Незареганный Alien!!!");
        } else if (c == MONSTER.ch()) {
            return monsters.get(founder.getName()).next();
        }

        return loader.load(c);
    }

    public static UnsupportedOperationException newObjectError(String c) {
        throw new UnsupportedOperationException("WTF! Новый объект в мире, а мы не в курсе: '" + c + "'");
    }

    @Override
    public void tick() {
        for (Something smth : getObjects()) {
            if (Tickable.class.isAssignableFrom(smth.getClass())) {
                ((Tickable)smth).tick();
            }
        }
    }

}
