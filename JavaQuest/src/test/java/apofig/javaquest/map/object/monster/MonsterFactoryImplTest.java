package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.Gold;
import apofig.javaquest.map.object.ObjectFactory;
import apofig.javaquest.map.object.Place;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        monster = factory.next();
        setupMonster(monster);
        assertEquals(PrimeFactoryMonster.class, monster.getClass());
        monster.answer(PrimeFactoryMonster.OK_CODE);

        monster = factory.next();
        setupMonster(monster);
        assertEquals(FibonacciNumbersMonster.class, monster.getClass());
        monster.answer(FibonacciNumbersMonster.OK_CODE);

        monster = factory.next();
        setupMonster(monster);
        assertEquals(SumSquareDifferenceMonster.class, monster.getClass());
        monster.answer(SumSquareDifferenceMonster.OK_CODE);

        monster = factory.next();
        setupMonster(monster);
        assertEquals(Monster.class, monster.getClass());
        monster.answer("die!");

        assertNotSame(monster, factory.next());
        monster = factory.next();
        setupMonster(monster);
        assertEquals(Monster.class, monster.getClass());
        monster.answer("die!");
    }

    private void setupMonster(Monster monster) {
        Messages messages = mock(Messages.class);
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
