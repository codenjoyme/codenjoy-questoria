package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Dieble;
import apofig.javaquest.map.object.*;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
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
    private Me hero;
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
            Askable gold = (Askable)leaveAfter();
            gold.ask();
        } else {
            messenger.say(help);
        }
    }

    @Override
    public boolean canLeave(Me hero) {
        boolean otherHeroCanLeave = this.hero == null || !this.hero.equals(hero);
        boolean canLeave = attemptsToLeave >= 1;
        return otherHeroCanLeave || canLeave;
    }

    @Override
    public void ask() {
        messenger.say(question);
    }

    @Override
    public Something leaveAfter() {
        return world.make('$', gold);
    }

    @Override
    public char symbol() {
        return '@';
    }

    @Override
    public void tryToLeave(Me hero) {
        if (hero.equals(this.hero)) {
            if (attemptsToLeave == 0) {
                messenger.say(leave);
            } else {
                // TODO потестить кейз, когда у игрока нет золота вообще, что тогда? Не отпускать его? :)
                messenger.say("Монстр отнял у тебя немного золота...");
                gold += this.hero.filchGold(GOLD_AMOUNT);
            }
            attemptsToLeave++;
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
            attemptsToLeave = 0;
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
