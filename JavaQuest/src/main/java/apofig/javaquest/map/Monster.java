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

    public Monster(String question, String answer, String help) {
        this.question = question;
        this.answer = answer;
        this.help = help;
    }

    @Override
    public void answer(String message) {
        if (message.equals(answer)) {
            say("yOU @#& Ki$%@&^ll me $!@!");
            Something something = leaveAfter();
            place.update(something.symbol());
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
