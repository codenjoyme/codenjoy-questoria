package com.codenjoy.dojo.questoria.model.items.monster;

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

import com.codenjoy.dojo.questoria.model.Messages;
import com.codenjoy.dojo.questoria.model.items.*;
import com.codenjoy.dojo.questoria.model.items.impl.Gold;
import com.codenjoy.dojo.questoria.model.items.monster.impl.FizzBuzzMonster;
import com.codenjoy.dojo.questoria.model.items.monster.impl.LongDivisionMonster;
import com.codenjoy.dojo.questoria.model.items.monster.impl.MakeBricksMonster;
import com.codenjoy.dojo.questoria.model.items.monster.impl.PrimeFactoryMonster;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Iterator;

import static com.codenjoy.dojo.questoria.model.items.monster.CodeRunnerMonster.ANSWER;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

public class MonsterPoolImplTest {

    private MonsterPool factory;
    private ArgumentCaptor<String> captor;
    private Messages messages;
    private Monster monster;

    @Before
    public void setup() {
        Iterable<Monster> monsters = mock(Iterable.class);
        Iterator<Monster> iterator = mock(Iterator.class);
        when(monsters.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, true, true, true, false);
        when(iterator.next()).thenReturn(new FizzBuzzMonster(), new PrimeFactoryMonster(), new LongDivisionMonster(), new MakeBricksMonster());

        factory = new MonsterPoolImpl(monsters);
    }

    @Test
    public void shouldReturnSameMonsterIfNoKill() {
        monster = factory.next();
        assertSame(monster, factory.next());
    }

    public final static String BAD_CODE_WITH_ONE_PARAMETER =
            "public String method(int number) { return \"-\"; }";

    public static final String BAD_CODE_WITH_TWO_PARAMETERS =
            "public String method(int a, int b) { return \"-\"; }";

    public static final String BAD_CODE_WITH_THREE_PARAMETERS =
            "public String method(int a, int b, int с) { return \"-\"; }";

    @Test
    public void shouldReturnOtherMonsterIfKillOrMessageOtherwise() {
        String badCode = "-\n-\n-\n-\n-\n-";
        String okCode = ANSWER;

        monster = factory.next();
        setupMonster(monster);

        assertEquals(FizzBuzzMonster.class, monster.getClass());
        monster.answer(okCode);

        assertMonster(PrimeFactoryMonster.class, badCode);
        assertEquals("[PrimeFactoryMonster: Для [1] метод должен вернуть “[1]”, но ты вернул “-”\n" +
                "Для [2] метод должен вернуть “[2]”, но ты вернул “-”\n" +
                "Для [3] метод должен вернуть “[3]”, но ты вернул “-”\n" +
                "Для [4] метод должен вернуть “[2,2]”, но ты вернул “-”\n" +
                "Для [5] метод должен вернуть “[5]”, но ты вернул “-”\n" +
                "Для [6] метод должен вернуть “[2,3]”, но ты вернул “-”" +
                ", PrimeFactoryMonster: Попробуй еще раз!]", getMessage());
        assertMonster(PrimeFactoryMonster.class, okCode);

        assertMonster(LongDivisionMonster.class, badCode);
        assertEquals("[LongDivisionMonster: Для [1, 2] метод должен вернуть “0.5”, но ты вернул “-”\n" +
                "Для [1, 1] метод должен вернуть “1”, но ты вернул “-”\n" +
                "Для [5, 5] метод должен вернуть “1”, но ты вернул “-”\n" +
                "Для [55, 5] метод должен вернуть “11”, но ты вернул “-”\n" +
                "Для [55, 44] метод должен вернуть “1.25”, но ты вернул “-”\n" +
                "Для [0, 56] метод должен вернуть “0”, но ты вернул “-”" +
                ", LongDivisionMonster: Попробуй еще раз!]", getMessage());
        assertMonster(LongDivisionMonster.class, okCode);

        assertMonster(MakeBricksMonster.class, badCode);
        assertEquals("[MakeBricksMonster: Для [0, 1, 5] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [1, 0, 1] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [1, 0, 2] метод должен вернуть “false”, но ты вернул “-”\n" +
                "Для [3, 1, 7] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [4, 2, 14] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [3, 2, 14] метод должен вернуть “false”, но ты вернул “-”" +
                ", MakeBricksMonster: Попробуй еще раз!]", getMessage());
        assertMonster(MakeBricksMonster.class, okCode);

        assertMonster(Monster.class, "die!");

        assertNotSame(monster, factory.next());
        monster = factory.next();
        setupMonster(monster);
        assertEquals(Monster.class, monster.getClass());
        monster.answer("die!");
    }

    private void assertMonster(Class<? extends Monster> expected, String okCode) {
        String message = getMessage();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, expected, monster.getClass());
        monster.answer(okCode);
    }

    private String getMessage() {
        verify(messages, atLeastOnce()).add(captor.capture());
        return captor.getAllValues().toString();
    }

    private void setupMonster(Monster monster) {
        captor = ArgumentCaptor.forClass(String.class);
        messages = mock(Messages.class);
        Messenger messenger = new MessengerImpl();
        monster.setMessenger(messenger);
        monster.getMessenger().add(messages);
        ObjectFactory objects = mock(ObjectFactory.class);
        Place place = mock(Place.class);
        Gold gold = new Gold();
        gold.setMessenger(new MessengerImpl());
        Me founder = null;
        when(objects.get(eq(place), eq(founder), any()))
                .thenAnswer(invocation -> {
                    monster.die();
                    return gold;
                });
        monster.setWorld(new WorldImpl(objects, place, monster, founder));
    }
}
