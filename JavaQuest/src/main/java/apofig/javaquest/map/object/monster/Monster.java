package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.Something;
import apofig.javaquest.map.object.TalkingObject;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
public class Monster extends TalkingObject implements Something {

    private String question;
    private String answer;
    protected String help;
    private String leave;
    private Action onKill;

    public Monster(String question, String answer, String help, String leave, Action onKill) {
        this.question = question;
        this.answer = answer;
        this.help = help;
        this.leave = leave;
        this.onKill = onKill;
    }

    @Override
    public void answer(String message) {
        if (message.equals(answer)) {
            say("тЫ @#& Уб$%@&^ил ме:ня $!@!");
            Something something = leaveAfter();
            place.update(something.symbol());
            if (onKill != null) {
                onKill.act(this);
            }
            something.askMe();
        } else {
            say(help);
        }
    }

    @Override
    public boolean iCanLeave() {
        return false;
    }

    @Override
    public void askMe() {
        say(question);
    }

    @Override
    public boolean iCanUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return make('$');
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
        say(leave);
    }
}
