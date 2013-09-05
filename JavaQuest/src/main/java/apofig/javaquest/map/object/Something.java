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
public interface Something extends CodeHelper {
    void answer(String message);

    boolean iCanLeave();

    void askMe();

    boolean iCanUse();

    Something leaveAfter();

    char symbol();

    void getBy(Player info);

    void tryToLeave();
}
