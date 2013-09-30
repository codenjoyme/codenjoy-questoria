package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Dieble;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.*;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
public class Monster extends TalkingObject implements Something, CodeHelper, Dieble, SetWorld, MeetWithHero, CanBeBusy, Leaveable {

    private String question;
    private String answer;
    protected String help;
    private String leave;
    private String signature;
    private Action onKill;
    private World world;
    private int complexity;
    private Me hero;

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
    }

    @Override
    public void answer(String message) {
        if (message.equals(answer)) {
            messenger.say("тЫ @#& Уб$%@&^ил ме:ня $!@!");
            Askable gold = (Askable)leaveAfter();
            gold.ask();
        } else {
            messenger.say(help);
        }
    }

    @Override
    public boolean canLeave(Me hero) {
        return this.hero == null || !this.hero.equals(hero);
    }

    @Override
    public void ask() {
        messenger.say(question);
    }

    @Override
    public Something leaveAfter() {
        return world.make('$');
    }

    @Override
    public char symbol() {
        return '@';
    }

    @Override
    public void tryToLeave(Me hero) {
        if (hero.equals(this.hero)) {
            messenger.say(leave);
        }
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
    public void meetWith(Me hero) {
        if (this.hero == null) {
            this.hero = hero;
        }
    }

    @Override
    public void leave(Me hero) {
        if (hero.equals(this.hero)) {
            this.hero = null;
        }
    }

    public int getComplexity() {
        return complexity;
    }
}
