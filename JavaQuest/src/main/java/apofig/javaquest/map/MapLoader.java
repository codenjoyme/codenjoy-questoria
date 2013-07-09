package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:05 PM
 */
public interface MapLoader {
    int getWidth();

    Map getMap();

    Map getFog();

    int getPlayerX();

    int getPlayerY();

    int getHeight();
}
