package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:53 PM
 */
public interface Joystick {
    void moveRight();

    void moveLeft();

    void moveUp();

    void moveDown();

    void attack(String message);
}
