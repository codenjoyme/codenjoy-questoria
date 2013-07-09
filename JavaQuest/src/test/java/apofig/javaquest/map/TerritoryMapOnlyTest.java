package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.ObjectFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: sanja
 * Date: 04.07.13
 * Time: 10:27
 */
public class TerritoryMapOnlyTest {

    @Test
    public void shouldGetViewArea() {
        ObjectFactory factory = mock(ObjectFactory.class);
        MapLoader loader = mock(MapLoader.class);

        int size = 21;

        Map fog = new Map(size, size, '?');
        Map map = new Map(size, size, ' ');

        when(loader.getFog()).thenReturn(fog);
        when(loader.getWidth()).thenReturn(size);
        when(loader.getHeight()).thenReturn(size);
        when(loader.getMap()).thenReturn(map);

        PlayerView view = new PlayerView(13);

        Me me = mock(Me.class);
        when(me.getX()).thenReturn(3);
        when(me.getY()).thenReturn(3);
        when(me.view()).thenReturn(view);
        view.moveMeTo(me);

        TerritoryMap territory = new TerritoryMapImpl(loader, factory);
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
