package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:29 PM
 */
public interface Something {
    String answer(String message);

    boolean iCanLeave();

    String askMe();

    boolean iCanUse();

    Something leaveAfter();

    char symbol();
}
