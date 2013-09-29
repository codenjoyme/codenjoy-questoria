package apofig.javaquest.map;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 19:48
 */
public class MapPlaceTest {

    private MapPlace place;

    @Before
    public void setup() {
        Map map = new Map(10, 10, ' ');
        map.set(3, 3, '1');  // 123
        map.set(4, 3, '2');  // 456
        map.set(5, 3, '3');  // 789
        map.set(3, 4, '4');
        map.set(4, 4, '5');
        map.set(5, 4, '6');
        map.set(3, 5, '7');
        map.set(4, 5, '8');
        map.set(5, 5, '9');
        place = new MapPlace(map, 4, 4);
    }

    @Test
    public void shouldWorkNear() {
        assertEquals("12369874", place.near());
    }

    @Test
    public void shouldWorkNearD() {
        assertEquals('9', place.near(1, 1));
        assertEquals('1', place.near(-1, -1));
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
        assertEquals("map[4,4]='5'", place.toString());
    }

    @Test
    public void shouldWorkUpdate() {
        assertEquals('5', place.getChar());
        place.update('A');
        assertEquals('A', place.getChar());
    }

}
