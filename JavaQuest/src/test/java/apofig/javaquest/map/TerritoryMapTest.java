package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.ObjectFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: sanja
 * Date: 04.07.13
 * Time: 10:27
 */
public class TerritoryMapTest {

    @Test
    public void shouldGetViewArea() {
        MapLoader loader = mock(MapLoader.class);

        int size = 21;

        Map map = new Map(size, size, ' ');

        when(loader.width()).thenReturn(size);
        when(loader.height()).thenReturn(size);
        when(loader.map()).thenReturn(map);

        PlayerView view = new PlayerView(13);

        Me me = mock(Me.class);
        when(me.getX()).thenReturn(3);
        when(me.getY()).thenReturn(3);
        when(me.isAt(new PointImpl(3, 3))).thenReturn(true);
        when(me.view()).thenReturn(view);
        view.moveMeTo(me);

        TerritoryMap territory = new TerritoryMap(loader);
        territory.newHero(me);
        territory.openSpace(me);

        String viewArea = territory.getViewArea(me);

        assertEquals(
                "╔══════════════════════════╗\n" +
                "║??????????????????????????║\n" +
                "║??????????      ??????????║\n" +
                "║??????              ??????║\n" +
                "║????##                ????║\n" +
                "║????##                ????║\n" +
                "║??####                  ??║\n" +
                "║??####      I           ??║\n" +
                "║??####                  ??║\n" +
                "║????##                ????║\n" +
                "║????##                ????║\n" +
                "║??????##############??????║\n" +
                "║??????????######??????????║\n" +
                "║??????????????????????????║\n" +
                "╚══════════════════════════╝", viewArea);
    }
}
