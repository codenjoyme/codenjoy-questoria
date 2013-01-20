package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 11:03 PM
 */
public class Gold implements Something {

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
        return "10 Gold";
    }

    @Override
    public boolean iCanUse() {
        return true;
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return '$';
    }
}
