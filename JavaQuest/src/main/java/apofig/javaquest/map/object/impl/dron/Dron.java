package apofig.javaquest.map.object.impl.dron;

import apofig.compiler.JavaCompiler;
import apofig.compiler.JavaMethod;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.*;
import apofig.javaquest.map.object.impl.Nothing;
import apofig.javaquest.map.object.monster.CodeHelper;
import apofig.javaquest.services.Tickable;

/**
 * User: sanja
 * Date: 01.09.13
 * Time: 2:25
 */
public class Dron extends TalkingObject implements Something, CodeHelper, Tickable, SetPlace, SetWorld {

    public static final char CHAR = '*';
    private Me hero;

    private String code =
            "public String whereToGo(String nearMe) {\n" +
            "    return \"|\";\n" +
            "}";

    private boolean active = false;

    private Place place;
    private World world;

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public void answer(String message) {
        this.code = message;
        messenger.sayOnce("Команда принята! Обработка начнется после того как ты отойдешь.");
    }

    @Override
    public boolean canLeave() {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Я твой робот! Запрограммируй меня.");
        active = false;
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return CHAR;
    }

    @Override
    public void getBy(Player info) {
        // do nothing
    }

    @Override
    public void tryToLeave() {
        messenger.sayOnce("Обработка началась!");
        active = true;

    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void tick() {
        if (active) {
            String whereToGo = runCode(code, place.near());   // TODO дрон может зависнуть, и вообще любой клиентский код может зависнуть
            int dx = 0;
            int dy = 0;

            if ("down".equals(whereToGo)) {
                dy = -1;
            } else if ("left".equals(whereToGo)) {
                dx = -1;
            } else if ("right".equals(whereToGo)) {
                dx = 1;
            } else if ("up".equals(whereToGo)) {
                dy = 1;
            } else {
                active = false;
                messenger.sayOnce("Команда '" + whereToGo + "' не принята! Остановка!!!");
                return;
            }

            char atWay = place.near(dx, dy);
            if (atWay == '$') {
                messenger.say("Дрон подобрал золото!");
                hero.getInfo().addGold(10); // TODO тут хардкод, это должно решать золото а не дрон
            }
            if (atWay == ' ' || atWay == '$') {
                world.move(place.getX() + dx, place.getY() + dy);
            } else {
                active = false;
                messenger.sayOnce("Дрон уперся в неопознанный объект! Остановка!!!");
            }
        }
    }

    private String runCode(String code, String near) {
        JavaCompiler compiler = new JavaCompiler();
        JavaMethod method;
        try {
            method = compiler.getMethod(code);
            return (String)method.run(near);
        } catch (Exception e) {  // TODO дублирование с CodeRunnerMonster
            if (e.getClass().equals(NullPointerException.class)) {
                return "Извини, NPE!";
            }
            System.out.println(e.toString());
            return (e.toString() + ": " +  e.getCause().getCause().toString()).replaceAll("Dynamic[0-9]+", "Dynamic"); // TODO hotfix
        }
    }

    public void setHero(Me hero) {
        this.hero = hero;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }
}
