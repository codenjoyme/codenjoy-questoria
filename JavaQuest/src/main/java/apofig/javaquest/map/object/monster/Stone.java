package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.Leaveable;
import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Something;
import apofig.javaquest.map.object.TalkingObject;
import apofig.javaquest.map.object.impl.Nothing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: sanja
 * Date: 12.09.13
 * Time: 19:41
 */
public class Stone extends TalkingObject implements Something, Leaveable {

    public static final String MESSAGE_1 = "Ты попал в сказочный мир Ascii кодов. Тут каждый объект живой. \n" +
            "С каждым из них ты сожешь взаимодействовать с помощью кода. \n" +
            "Этот мир не безопасен - тебе будут встречаться различные его обитатели. \n" +
            "Некоторые из них хотят тебе навредить, другие - помочь, \n" +
            "третьи - делают вид, что помогают. Будь внимателен (дальше...)";

    public static final String MESSAGE_2 = "Неподалеку, на юго западе есть золотое поле. Сходи, разведай. \n" +
            "Золото тебе пригодится вдальнейшем. По дороге будут встречаться монстры. \n" +
            "Чтобы избавиться от них - достаточно решить предложенную головоломку.";

    public static final String MESSAGE_INTRO = "На камне что-то написано (нажми say чтобы посмотреть...)";

    public static final String MESSAGE_GOODBYE = "Успехов, Странник!";

    private SimpleIterator<String> iterator = new SimpleIterator<String>(Arrays.asList(MESSAGE_1, MESSAGE_2));

    @Override
    public void answer(String message) {
        if (iterator.hasNext()) {
            messenger.sayOnce(iterator.next());
        }
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        iterator.reset();
        messenger.sayOnce(MESSAGE_INTRO);
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return 'O';
    }

    @Override
    public void tryToLeave(Me hero) {
        iterator.reset();
        messenger.sayOnce(MESSAGE_GOODBYE);
    }
}
