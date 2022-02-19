package apofig.javaquest.field;

import apofig.javaquest.field.object.Me;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TerritoryFieldTest {

    @Test
    public void shouldGetViewArea() {
        FieldLoader loader = mock(FieldLoader.class);

        int size = 21;

        Field field = new Field(size, size, ' ');

        when(loader.width()).thenReturn(size);
        when(loader.height()).thenReturn(size);
        when(loader.field()).thenReturn(field);

        PlayerView view = new PlayerView(13);

        Me me = mock(Me.class);
        when(me.getX()).thenReturn(3);
        when(me.getY()).thenReturn(3);
        when(me.isAt(new PointImpl(3, 3))).thenReturn(true);
        when(me.view()).thenReturn(view);
        view.moveMeTo(me);

        TerritoryField territory = new TerritoryField(loader);
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
