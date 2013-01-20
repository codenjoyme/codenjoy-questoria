package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:43 PM
 */
public class Nothing implements Something {
    @Override
    public String say(String message) {
        return null;
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public String askMe() {
        return null;
    }
}
