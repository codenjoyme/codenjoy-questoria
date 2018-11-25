package apofig.javaquest.field;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:05 PM
 */
public interface FieldLoader {
    Field field();

    Point initPosition();

    int height();
    int width();
}
