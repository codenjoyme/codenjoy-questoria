package com.codenjoy.dojo.questoria.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.questoria.model.items.Me;
import org.junit.Test;

import static com.codenjoy.dojo.questoria.client.Element.NOTHING;
import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TerritoryFieldTest {

    @Test
    public void shouldGetViewArea() {
        FieldLoader loader = mock(FieldLoader.class);

        int size = 21;

        FieldOld field = new FieldOld(size, NOTHING.ch());

        when(loader.size()).thenReturn(size);
        when(loader.field()).thenReturn(field);

        PlayerView view = new PlayerView(13);

        Me me = mock(Me.class);
        when(me.getX()).thenReturn(3);
        when(me.getY()).thenReturn(3);
        when(me.itsMe(pt(3, 3))).thenReturn(true);
        when(me.view()).thenReturn(view);
        view.moveMeTo(me);

        TerritoryField territory = new TerritoryField(loader);
        territory.newHero(me);
        territory.openSpace(me);

        String viewArea = territory.getViewArea(me);

        assertEquals(
                "?????????????\n" +
                "?????   ?????\n" +
                "???       ???\n" +
                "??#        ??\n" +
                "??#        ??\n" +
                "?##         ?\n" +
                "?##   I     ?\n" +
                "?##         ?\n" +
                "??#        ??\n" +
                "??#        ??\n" +
                "???#######???\n" +
                "?????###?????\n" +
                "?????????????\n", viewArea);
    }
}
