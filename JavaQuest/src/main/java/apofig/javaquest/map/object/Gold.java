package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 11:03 PM
 */
public class Gold extends TalkingObject implements Something {

    @Override
    public void answer(String message) {
        sayUnique("Ты не можешь делать это со мной!");
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        sayUnique("Привет, я - 10$");
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
        leaveAfter();
        say("Ты подобрал меня! Спасибо!!");
    }

    @Override
    public void tryToLeave() {
        sayUnique("Ну и ладно! Достанусь кому-то другому!!");
    }

    @Override
    public void onKill(Action action) {
        // do nothing
    }

    @Override
    public String getCode() {
        return "";
    }
}
