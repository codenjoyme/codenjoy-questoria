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

import com.codenjoy.dojo.questoria.client.Element;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.joystick.Act;
import com.codenjoy.dojo.services.joystick.RoundsDirectionActJoystick;
import com.codenjoy.dojo.services.printer.state.HeroState;
import com.codenjoy.dojo.services.printer.state.State;
import com.codenjoy.dojo.services.round.RoundPlayerHero;

import static com.codenjoy.dojo.questoria.client.Element.HERO;
import static com.codenjoy.dojo.questoria.client.Element.HERO_DEAD;
import static com.codenjoy.dojo.questoria.client.ElementUtils.TEAM_ELEMENT;
import static com.codenjoy.dojo.questoria.services.Event.Type.HERO_DIED;

public class Hero extends RoundPlayerHero<Field>
        implements RoundsDirectionActJoystick,
                   State<Element, Player>,
                   HeroState<Element, Hero, Player> {

    private int score;
    private Direction direction;

    public Hero(Point pt) {
        super(pt);
        clearScores();
    }

    public void clearScores() {
        score = 0;
        direction = null;
    }

    @Override
    public void init(Field field) {
        super.init(field);

        field.heroes().add(this);
    }

    @Override
    public void change(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void act(Act act) {
        if (act.is()) {
            // TODO implement me
            return;
        }

        // if (act.is(OTHER_VALUE)) {
        //     someAction = true;
        //     return;
        // }
    }

    @Override
    public void die() {
        die(HERO_DIED);
    }

    @Override
    public void tickHero() {
        if (direction != null) {
            Point to = direction.change(this.copy());

            if (!field.isBarrier(to)) {
                move(to);
            }
        }
        direction = null;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return HeroState.super.state(player, TEAM_ELEMENT, alsoAtPoint);
    }

    @Override
    public Element beforeState(Object[] alsoAtPoint) {
        return isActiveAndAlive()
                ? HERO
                : HERO_DEAD;
    }

    @Override
    public int scores() {
        return score;
    }

    public void addScore(int added) {
        score = Math.max(0, score + added);
    }

}