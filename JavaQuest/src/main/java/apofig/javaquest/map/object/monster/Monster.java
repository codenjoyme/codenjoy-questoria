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
public class Monster extends TalkingObject implements Something, Dieble, SetWorld {

    private String question;
    private String answer;
    protected String help;
    private String leave;
    private String signature;
    private Action onKill;
    private World world;

    public Monster(String question, String answer,
                   String help, String leave,
                   String signature)
    {
        this.question = question;
        this.answer = answer;
        this.help = help;
        this.leave = leave;
        this.signature = signature;
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
    public boolean iCanLeave() {
        return false;
    }

    @Override
    public void ask() {
        messenger.say(question);
    }

    @Override
    public boolean iCanUse() {
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
}
