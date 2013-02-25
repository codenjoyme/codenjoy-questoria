package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.Gold;
import apofig.javaquest.map.object.ObjectFactory;
import apofig.javaquest.map.object.Place;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 10:58 PM
 */
public class MonsterFactoryImplTest {

    @Test
    public void shouldReturnSameMonsterIfNoKill() {
        MonsterPool factory = new MonsterFactoryImpl();
        Monster monster = factory.next();
        assertSame(monster, factory.next());
    }

    @Test
    public void shouldReturnOtherMonsterIfKill() {
        MonsterFactoryImpl factory = new MonsterFactoryImpl();
        Monster monster = factory.next();
        setupMonster(monster);

        assertEquals(FizzBuzzMonster.class, monster.getClass());
        monster.answer(FizzBuzzMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        String message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, PrimeFactoryMonster.class, monster.getClass());
        monster.answer(PrimeFactoryMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, FibonacciNumbersMonster.class, monster.getClass());
        monster.answer(FibonacciNumbersMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, SumSquareDifferenceMonster.class, monster.getClass());
        monster.answer(SumSquareDifferenceMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, XthPrimeMonster.class, monster.getClass());
        monster.answer(XthPrimeMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, PowerDigitSumMonster.class, monster.getClass());
        monster.answer(PowerDigitSumMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, FactorialMonster.class, monster.getClass());
        monster.answer(FactorialMonster.OK_CODE);

        verify(messages, atLeastOnce()).add(captor.capture());
        message = captor.getAllValues().toString();

        monster = factory.next();
        setupMonster(monster);
        assertEquals(message, Monster.class, monster.getClass());
        monster.answer("die!");

        assertNotSame(monster, factory.next());
        monster = factory.next();
        setupMonster(monster);
        assertEquals(Monster.class, monster.getClass());
        monster.answer("die!");
    }

    ArgumentCaptor<String> captor;
    Messages messages;
    private void setupMonster(Monster monster) {
        captor = ArgumentCaptor.forClass(String.class);
        messages = mock(Messages.class);
        monster.setMessages(messages);
        ObjectFactory objects = mock(ObjectFactory.class);
        Place place = mock(Place.class);
        Gold gold = new Gold();
        gold.setMessages(messages);
        when(objects.make('$', place)).thenReturn(gold);
        monster.setFactory(objects);
        monster.setPlace(place);
    }
}
