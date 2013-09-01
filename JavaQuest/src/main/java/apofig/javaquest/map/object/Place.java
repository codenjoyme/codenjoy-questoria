package apofig.javaquest.map.object;

import apofig.javaquest.map.Point;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 6:57 PM
 */
public interface Place extends Point {
    void update(char newChar);

    char getChar();

    String near();

    char near(int dx, int dy);
}
