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
public class Monster extends TalkingObject implements Something, CodeHelper, Dieble, SetWorld {

    private String question;
    private String answer;
    protected String help;
    private String leave;
    private String signature;
    private Action onKill;
    private World world;
    private int weight;

    public Monster(String question, String answer,
                   String help, String leave,
                   String signature, int weight)
    {
        this.question = question;
        this.answer = answer;
        this.help = help;
        this.leave = leave;
        this.signature = signature;
        this.weight = weight;
    }

    @Override
    public void answer(String message) {
        if (message.equals(answer)) {
            messenger.say("тЫ @#& Уб$%@&^ил ме:ня $!@!");
            Something gold = leaveAfter();
            gold.ask();
        } else {
            messenger.say(help);
        }
    }

    @Override
    public boolean canLeave() {
        return false;
    }

    @Override
    public void ask() {
        messenger.say(question);
    }

    @Override
    public boolean canUse() {
        return false;
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
    public void getBy(Player info) {
        // do nothing
    }

    @Override
    public void tryToLeave() {
        messenger.say(leave);
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

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("[Monster: %s with weight: %s]", this.getClass().getSimpleName(), weight);
    }
}
