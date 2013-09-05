package apofig.javaquest.map.object.dron;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.Me;
import apofig.javaquest.map.object.Something;
import apofig.javaquest.map.object.TalkingObject;

/**
 * User: sanja
 * Date: 01.09.13
 * Time: 2:02
 */
public class DronMentor extends TalkingObject implements Something {

    public static final String MESSAGE = "Видишь все это золото? Оно твое. Ты можешь собрать его намного быстрее, \n" +
            "если воспользуешься помощником. Я научу тебя его делать. \n" +
            "Чтобы робот заработал, тебе нужно наделить его разумом. \n" +
            "    public String whereToGo(String nearMe) {\n" +
            "        return \"command\";\n" +
            "    }\n" +
            "Реализуй этот метод и посмотрим что у тебя получится. \n" +
            "Строка nearMe - это то что видит робо вокруг себя. Расшифруй ее так\n" +
            "      123\n" +
            "      8*4\n" +
            "      765\n" +
            "Команды управления робота - left, right, up, down.\n" +
            "Если робот убежит, тебе прийдется его искать. \n" +
            "Только находясь рядом с ним ты можешь его модифицировать. \n" +
            "При виде тебя робот останавливается и ждет твоей команды. \n" +
            "Нажми say и твой робот появится...";
    private Me hero;

    @Override
    public void answer(String message) {
        Dron dron = leaveAfter();
        dron.meetWith(hero);
        dron.askMe();
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        sayOnce(MESSAGE);
    }

    @Override
    public void meetWith(Me me) {
        this.hero = me;
        super.meetWith(me);
    }

    @Override
    public boolean iCanUse() {
        return false;  
    }

    @Override
    public Dron leaveAfter() {
        Dron dron = (Dron)make(Dron.CHAR);
        dron.setHero(hero);
        return dron;
    }

    @Override
    public char symbol() {
        return 'M';
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }

    @Override
    public void tryToLeave() {
        // do nothing
    }

    @Override
    public String getCode() {
        return "";
    }
}
