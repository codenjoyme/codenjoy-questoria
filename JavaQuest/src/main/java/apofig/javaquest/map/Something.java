package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:29 PM
 */
public interface Something {
    void answer(String message);

    boolean iCanLeave();

    void askMe();

    boolean iCanUse();

    Something leaveAfter();

    char symbol();
}
