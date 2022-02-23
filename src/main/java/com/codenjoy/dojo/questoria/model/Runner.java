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

import com.codenjoy.dojo.questoria.services.GameSettings;
import com.codenjoy.dojo.questoria.services.saver.Loader;
import com.codenjoy.dojo.questoria.services.saver.Saver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Tickable;
import com.codenjoy.dojo.services.dice.RandomDice;

import java.util.*;

import static com.codenjoy.dojo.services.multiplayer.LevelProgress.levelsStartsFrom1;

public class Runner implements Tickable {

    private GameSettings settings;
    private QuestoriaGame game;
    private java.util.Map<String, PlayerOld> players;

    public Runner() {}

    public Runner(String map) {
        settings = new GameSettings();
        if (map != null) {
            settings.setLevelMap(levelsStartsFrom1, map);
        }
        Dice dice = new RandomDice();
        Level level = settings.level(levelsStartsFrom1, dice, Level::new);
        game = new QuestoriaGame(dice, level, settings);
        players = new HashMap<>();
    }

    @Override
    public void tick() {
        game.tick();
    }

    public PlayerOld getPlayerByCode(String playerGameCode) {
        return players.get(playerGameCode);
    }

    public boolean alreadyRegistered(String playerName) {
        return getPlayerByName(playerName) != null;
    }

    private PlayerOld getPlayerByName(String playerName) {
        for (Map.Entry<String, PlayerOld> entry : players.entrySet()) {
            if (entry.getValue().getName().equals(playerName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public String register(String playerName) {
        PlayerOld player = new PlayerOld(playerName, game);
        players.put(player.getGameCode(), player);
        return player.getGameCode();
    }

    public List<PlayerOld> players() {
        List<PlayerOld> result = new LinkedList<>(players.values());
        Collections.sort(result, Comparator.comparing(PlayerOld::getName));
        return result;
    }

    public void saveAllGame() {
        String json = new Saver().save(this);
        new WithFile("save_game.json").save(json);
    }

    public void loadAllGame() {
        String json = new WithFile("save_game.json").load();
        Runner service = (Runner)new Loader().load(json);
        this.game = service.game;
        this.players = service.players;
    }

    public void remove(String playerName) {
        PlayerOld player = getPlayerByName(playerName);
        players.remove(player.getGameCode());
        game.remove(player.getName());
    }

    public GameSettings settings() {
        return settings;
    }
}
