package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 11:03 PM
 */
public class Gold extends TalkingObject implements Something, SetWorld {

    private World world;

    @Override
    public void answer(String message) {
        messenger.sayOnce("Ты не можешь делать это со мной!");
    }

    @Override
    public boolean canLeave() {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Привет, я - 10$");
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public Something leaveAfter() {
        return world.make(' ');
    }

    @Override
    public char symbol() {
        return '$';
    }

    @Override
    public void getBy(Player player) {
        player.addGold(10);
        leaveAfter();
        messenger.say("Ты подобрал меня! Спасибо!!");
    }

    @Override
    public void tryToLeave() {
        messenger.sayOnce("Ну и ладно! Достанусь кому-то другому!!");
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
