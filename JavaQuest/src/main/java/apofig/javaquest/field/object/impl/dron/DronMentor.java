package apofig.javaquest.field.object.impl.dron;

import apofig.javaquest.field.object.*;

public class DronMentor extends TalkingObject implements Something, SetWorld, MeetWithHero {

    public static final String MESSAGE =
            "Видишь все это золото? Оно твое. Ты можешь собрать его намного быстрее, \n" +
            "если воспользуешься помощником. Я научу тебя его делать. \n" +
            "Чтобы робот заработал, тебе нужно наделить его разумом. \n" +
            "    public String whereToGo(String nearMe) {\n" +
            "        return \"command\";\n" +
            "    }\n" +
            "Реализуй этот метод и посмотрим что у тебя получится. \n" +
            "Строка nearMe - это то что видит робо вокруг себя. Расшифруй ее так\n" +
            "      012\n" +
            "      7*3\n" +
            "      654\n" +
            "Команды управления робота - left, right, up, down.\n" +
            "Если робот убежит, тебе прийдется его искать. \n" +
            "Только находясь рядом с ним ты можешь его модифицировать. \n" +
            "При виде тебя робот останавливается и ждет твоей команды. \n" +
            "Нажми say и твой робот появится...";

    private Me hero;
    private World world;

    @Override
    public void answer(String message) {
        Dron dron = leaveAfter();
        dron.ask();
    }

    @Override
    public void ask() {
        messenger.sayOnce(MESSAGE);
    }

    @Override
    public Dron leaveAfter() {
        Dron dron = (Dron)world.make(Dron.CHAR);
        dron.setHero(hero);
        return dron;
    }

    @Override
    public void meetWith(Me hero) {
        this.hero = hero;
    }

    @Override
    public void leave(Me hero) {
        // do nothing
    }

    @Override
    public char symbol() {
        return 'M';
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
