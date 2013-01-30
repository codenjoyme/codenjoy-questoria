package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:43 PM
 */
public class Nothing implements Something {
    @Override
    public String answer(String message) {
        return "";
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public String askMe() {
        return "";
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
        return ' ';
    }
}
