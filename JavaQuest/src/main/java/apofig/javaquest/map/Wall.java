package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 10:25 PM
 */
public class Wall implements Something {
    @Override
    public String answer(String message) {
        return null;
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public String askMe() {
        return "Wall: Please stop!";
    }

    @Override
    public boolean iCanUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return '#';
    }
}
