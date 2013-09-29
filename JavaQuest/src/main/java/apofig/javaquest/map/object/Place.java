package apofig.javaquest.map.object;

import apofig.javaquest.map.Point;

import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 6:57 PM
 */
public interface Place extends Point {
    void update(char newChar);

    char getChar();

    List<Place> near();

    Place near(int dx, int dy);

    void move(int x, int y);
}
