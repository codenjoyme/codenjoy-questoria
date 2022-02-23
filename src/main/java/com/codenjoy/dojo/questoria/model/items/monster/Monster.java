package com.codenjoy.dojo.questoria.model.items.monster;

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
import com.codenjoy.dojo.questoria.model.Action;
import com.codenjoy.dojo.questoria.model.Dieble;
import com.codenjoy.dojo.questoria.model.Player;
import com.codenjoy.dojo.questoria.model.items.*;

import static com.codenjoy.dojo.questoria.client.Element.GOLD;

public class Monster extends TalkingObject implements Something, CodeHelper, Dieble, SetWorld, MeetWithHero, CanBeBusy, Leaveable {

    public static final int GOLD_AMOUNT = 10;
    private String question;
    private String answer;
    protected String help;
    private String leave;
    private String signature;
    private Action onKill;
    private World world;
    private int complexity;
    protected Me hero;
    private int gold;
    private int attemptsToLeave;

    public Monster(String question, String answer,
                   String help, String leave,
                   String signature, int complexity)
    {
        this.question = question;
        this.answer = answer;
        this.help = help;
        this.leave = leave;
        this.signature = signature;
        this.complexity = complexity;
        this.gold = GOLD_AMOUNT;
    }

    @Override
    public void answer(String message) {
        if (message.equals(answer)) {
            messenger.say("тЫ @#& Уб$%@&^ил ме:ня $!@!");
            Askable gold = (Askable) leaveAfter();
            gold.ask();
        } else {
            messenger.say(help);
        }
    }

    @Override
    public boolean canLeave(Me other) {
        boolean otherHeroCanLeave = hero == null || !hero.equals(other);
        boolean canLeave = attemptsToLeave >= 1;
        return otherHeroCanLeave || canLeave;
    }

    @Override
    public void ask() {
        messenger.say(question);
    }

    @Override
    public Something leaveAfter() {
        return world.make(GOLD.ch(), gold);
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.MONSTER;
    }

    @Override
    public void tryToLeave(Me hero) {
        if (!hero.equals(this.hero)) {
            return;
        }

        if (attemptsToLeave == 0) {
            messenger.say(leave);
        } else {
            leaved(this.hero);
        }
        attemptsToLeave++;
    }

    @Override
    public void leaved(Me hero) {
        // TODO потестить кейз, когда у игрока нет золота вообще, что тогда? Не отпускать его? :)
        messenger.say("Монстр отнял у тебя немного золота...");
        gold += this.hero.filchGold(GOLD_AMOUNT);
    }

    @Override
    public void sayWhenBusy() {
        messenger.sayToLast("Я сейчас занят!");
    }

    @Override
    public boolean isBusy() {
        return hero != null;
    }

    public void onKill(Action action) {
        onKill = action;
    }

    @Override
    public void die() {
        if (onKill != null) {
            onKill.act(this);
        }
        if (hero != null) {
            hero.closePortal(this);
        }
    }

    @Override
    public String getCode() {
        return signature;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public String toString() {
        return String.format("[Monster: %s with complexity: %s]", this.getClass().getSimpleName(), complexity);
    }

    @Override
    public void meetWith(Me someHero) {
        if (hero == null) {
            hero = someHero;
            hero.openPortal(this);
            attemptsToLeave = 0;
        }
    }

    @Override
    public void leave(Me someHero) {
        if (someHero.equals(hero)) {
            hero.closePortal(this);
            hero = null;
        }
    }

    public int getComplexity() {
        return complexity;
    }
}
