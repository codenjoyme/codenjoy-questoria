package apofig.javaquest.field.object;

/**
 * User: sanja
 * Date: 19.09.13
 * Time: 23:19
 */
public interface Leaveable {

    default boolean canLeave(Me hero) {
        return true;
    }

    default void tryToLeave(Me hero) {
        leaved(hero);
    }

    default void leaved(Me hero) {
        // do nothing
    }
}
