package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 11:03 PM
 */
public class Gold extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        say("You can't do this!");
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        say("I am an 10$");
    }

    @Override
    public boolean iCanUse() {
        return true;
    }

    @Override
    public Something leaveAfter() {
        return make(' ');
    }

    @Override
    public char symbol() {
        return '$';
    }

    @Override
    public void getBy(Player player) {
        player.addGold(10);
        clearLog();
        say("You pick up me! Thanks!!");
    }
}
