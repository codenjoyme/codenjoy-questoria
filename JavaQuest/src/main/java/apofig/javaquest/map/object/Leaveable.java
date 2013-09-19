package apofig.javaquest.map.object;

/**
 * User: sanja
 * Date: 19.09.13
 * Time: 23:19
 */
public interface Leaveable {
    boolean canLeave(Me hero);

    void tryToLeave(Me hero);
}
