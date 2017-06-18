package apofig.javaquest.map.object.impl;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.*;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 11:03 PM
 */
public class Gold extends TalkingObject implements Something, SetWorld, SetParameters<Integer>, Leaveable, Usable {

    private World world;
    private int amount;

    @Override
    public void answer(String message) {
        messenger.sayOnce("Ты не можешь делать это со мной!");
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Привет, я - " + amount + "$");
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
        player.addGold(amount);
        leaveAfter();
        messenger.say("Ты подобрал меня! Спасибо!!");
    }

    @Override
    public void tryToLeave(Me hero) {
        messenger.sayOnce("Ну и ладно! Достанусь кому-то другому!!");
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void setParameters(Integer amount) {
        if (amount == null) {
            amount = 1; // TODO потестить этот кейз
        }
        this.amount = amount;
    }
}
