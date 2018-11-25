package apofig.javaquest.field;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 19:48
 */
public class FieldPlaceTest {

    private FieldPlace place;

    @Before
    public void setup() {
        Field field = new Field(10, 10, ' ');
        field.set(3, 3, '1');  // 123
        field.set(4, 3, '2');  // 456
        field.set(5, 3, '3');  // 789
        field.set(3, 4, '4');
        field.set(4, 4, '5');
        field.set(5, 4, '6');
        field.set(3, 5, '7');
        field.set(4, 5, '8');
        field.set(5, 5, '9');
        place = new FieldPlace(field, 4, 4);
    }

    @Test
    public void shouldWorkNear() {
        assertEquals(
                "[field[4,3]='2', field[5,4]='6', field[4,5]='8', field[3,4]='4']", place.near().toString());
    }

    @Test
    public void shouldWorkNearD() {
        assertEquals("field[5,5]='9'", place.near(1, 1).toString());
        assertEquals("field[3,3]='1'", place.near(-1, -1).toString());
    }


    @Test
    public void shouldWorkMove() {
        place.move(place.getX(), place.getY() + 1);
        assertTrue(place.isAt(new PointImpl(4, 5)));

        assertEquals('8', place.getChar());

        place.move(place.getX() + 1, place.getY());
        assertTrue(place.isAt(new PointImpl(5, 5)));

        assertEquals('9', place.getChar());
    }

    @Test
    public void shouldToString() {
        assertEquals("field[4,4]='5'", place.toString());
    }

    @Test
    public void shouldWorkUpdate() {
        assertEquals('5', place.getChar());
        place.update('A');
        assertEquals('A', place.getChar());
    }

}
