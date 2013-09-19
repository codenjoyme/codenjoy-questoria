package apofig.javaquest.map.object;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.Point;
import apofig.javaquest.map.object.monster.CodeHelper;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:29 PM
 */
public interface Something {   // TODO раздеребанить интерфейс на более мелкие по образу иподобию CanBeBusy
    void answer(String message);
    void ask();

    boolean canUse();
    void getBy(Player info);

    Something leaveAfter();

    char symbol();

}
