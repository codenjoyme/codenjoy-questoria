package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
public class Monster extends TalkingObject implements Something {

    private String question;
    private String answer;
    private String help;
    private Action onKill;

    public Monster(String question, String answer, String help, Action onKill) {
        this.question = question;
        this.answer = answer;
        this.help = help;
        this.onKill = onKill;
    }

    @Override
    public void answer(String message) {
        if (message.equals(answer)) {
            say("yOU @#& Ki$%@&^ll me $!@!");
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
}
