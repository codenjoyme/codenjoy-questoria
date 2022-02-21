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

import com.codenjoy.dojo.questoria.model.items.impl.Gold;
import com.codenjoy.dojo.questoria.model.items.impl.Wall;
import com.codenjoy.dojo.questoria.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.field.Accessor;
import com.codenjoy.dojo.services.field.Generator;
import com.codenjoy.dojo.services.field.PointField;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.services.round.RoundField;
import com.codenjoy.dojo.utils.whatsnext.WhatsNextUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.codenjoy.dojo.questoria.services.Event.Type.START_ROUND;
import static com.codenjoy.dojo.questoria.services.Event.Type.WIN_ROUND;

public class Questoria extends RoundField<Player, Hero> implements Field {

    private Level level;
    private PointField field;
    private List<Player> players;
    private Dice dice;
    private GameSettings settings;

    public Questoria() {
        // do nothing, for testing only
    }

    public Questoria(Dice dice, Level level, GameSettings settings) {
        super(START_ROUND, WIN_ROUND, settings);

        this.level = level;
        this.dice = dice;
        this.settings = settings;
        this.field = new PointField();
        this.players = new LinkedList<>();

        clearScore();
    }

    @Override
    public void clearScore() {
        if (level == null) return;

        level.saveTo(field);
        field.init(this);

        // other clear score actions

        super.clearScore();
    }

    @Override
    public void onAdd(Player player) {
        player.newHero(this);
    }

    @Override
    public void onRemove(Player player) {
        heroes().removeExact(player.getHero());
    }

    @Override
    protected List<Player> players() {
        return players;
    }

    @Override
    public void cleanStuff() {
        // clean all temporary stuff before next tick
    }

    @Override
    protected void setNewObjects() {
        // add new object after rewarding winner
    }

    @Override
    public void tickField() {
        for (Player player : players) {
            Hero hero = player.getHero();

            hero.tick();

            // TODO call real game
        }
    }

    public int size() {
        return field.size();
    }

    @Override
    public boolean isBarrier(Point pt) {
        return pt.isOutOf(size())
                || walls().contains(pt)
                || heroes().contains(pt);
    }

    @Override
    public Optional<Point> freeRandom(Player player) {
        return Generator.freeRandom(size(), dice, this::isFree);
    }

    @Override
    public boolean isFree(Point pt) {
        return !(gold().contains(pt)
                || walls().contains(pt)
                || heroes().contains(pt));
    }

    @Override
    public GameSettings settings() {
        return settings;
    }

    @Override
    public BoardReader<Player> reader() {
        return field.reader(
                Hero.class,
                Gold.class,
                Wall.class);
    }

    @Override
    public List<Player> load(String board, Function<Hero, Player> player) {
        level = new Level(board);
        return WhatsNextUtils.load(this, level.heroes(), player);
    }

    @Override
    public Accessor<Hero> heroes() {
        return field.of(Hero.class);
    }

    @Override
    public Accessor<Gold> gold() {
        return field.of(Gold.class);
    }

    @Override
    public Accessor<Wall> walls() {
        return field.of(Wall.class);
    }

}