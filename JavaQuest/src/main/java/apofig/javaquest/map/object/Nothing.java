package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:43 PM
 */
public class Nothing extends TalkingObject implements Something, SetWorld {

    private World world;

    @Override
    public void answer(String message) {
        say("Ну и?..");
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void ask() {
//        clearLog();  // TODO реализовать очистку лога после двух подряд Nothing
    }

    @Override
    public boolean iCanUse() {
        return true;
    }

    @Override
    public Something leaveAfter() {
        return world.make(' ');
    }

    @Override
    public char symbol() {
        return ' ';
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }

    @Override
    public void tryToLeave() {
        // do nothing
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
