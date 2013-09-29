package apofig.javaquest.map.object;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:29 PM
 */
public interface Something {   // TODO раздеребанить интерфейс на более мелкие по образу иподобию CanBeBusy
    void answer(String message);
    void ask();

    Something leaveAfter();

    char symbol();

}
