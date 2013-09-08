package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Messages;
import apofig.javaquest.map.object.*;
import apofig.javaquest.map.object.impl.Gold;
import apofig.javaquest.map.object.monster.impl.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Iterator;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 10:58 PM
 */
public class MonsterFactoryImplTest {

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

        factory = new MonsterFactoryImpl(monsters);
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
        monster = factory.next();
        setupMonster(monster);

        assertEquals(FizzBuzzMonster.class, monster.getClass());
        monster.answer(FizzBuzzMonster.OK_CODE);

        assertMonster(PrimeFactoryMonster.class, BAD_CODE_WITH_ONE_PARAMETER);
        assertEquals("[PrimeFactoryMonster: Для [1] метод должен вернуть “[1]”, но ты вернул “-”\n" +
                "Для [2] метод должен вернуть “[2]”, но ты вернул “-”\n" +
                "Для [3] метод должен вернуть “[3]”, но ты вернул “-”\n" +
                "Для [4] метод должен вернуть “[2,2]”, но ты вернул “-”\n" +
                "Для [5] метод должен вернуть “[5]”, но ты вернул “-”\n" +
                "Для [6] метод должен вернуть “[2,3]”, но ты вернул “-”\n" +
                "Для [7] метод должен вернуть “[7]”, но ты вернул “-”\n" +
                "..., PrimeFactoryMonster: Попробуй еще раз!]", getMessage());
        assertMonster(PrimeFactoryMonster.class, PrimeFactoryMonster.OK_CODE);

        assertMonster(LongDivisionMonster.class, BAD_CODE_WITH_TWO_PARAMETERS);
        assertEquals("[LongDivisionMonster: Для [1, 2] метод должен вернуть “0.5”, но ты вернул “-”\n" +
                "Для [1, 1] метод должен вернуть “1”, но ты вернул “-”\n" +
                "Для [5, 5] метод должен вернуть “1”, но ты вернул “-”\n" +
                "Для [55, 5] метод должен вернуть “11”, но ты вернул “-”\n" +
                "Для [55, 44] метод должен вернуть “1.25”, но ты вернул “-”\n" +
                "Для [0, 56] метод должен вернуть “0”, но ты вернул “-”\n" +
                "Для [56, 1] метод должен вернуть “56”, но ты вернул “-”\n" +
                "..., LongDivisionMonster: Попробуй еще раз!]", getMessage());
        assertMonster(LongDivisionMonster.class, LongDivisionMonster.OK_CODE);

        assertMonster(MakeBricksMonster.class, BAD_CODE_WITH_THREE_PARAMETERS);
        assertEquals("[MakeBricksMonster: Для [0, 1, 5] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [1, 0, 1] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [1, 0, 2] метод должен вернуть “false”, но ты вернул “-”\n" +
                "Для [3, 1, 7] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [4, 2, 14] метод должен вернуть “true”, но ты вернул “-”\n" +
                "Для [3, 2, 14] метод должен вернуть “false”, но ты вернул “-”\n" +
                "Для [0, 2, 5] метод должен вернуть “true”, но ты вернул “-”\n" +
                "..., MakeBricksMonster: Попробуй еще раз!]", getMessage());
        assertMonster(MakeBricksMonster.class, MakeBricksMonster.OK_CODE);

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

    private void setupMonster(final Monster monster) {
        captor = ArgumentCaptor.forClass(String.class);
        messages = mock(Messages.class);
        Messenger messenger = new MessengerImpl();
        monster.setMessenger(messenger);
        monster.getMessenger().add(messages);
        ObjectFactory objects = mock(ObjectFactory.class);
        Place place = mock(Place.class);
        final Gold gold = new Gold();
        gold.setMessenger(new MessengerImpl());
        when(objects.get(place)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                monster.die();
                return gold;
            }
        });
        monster.setWorld(new WorldImpl(objects, place, monster));
    }
}
