package apofig.javaquest.field.object.impl;

import apofig.javaquest.field.Player;
import apofig.javaquest.field.object.*;

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
