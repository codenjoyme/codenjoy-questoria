package apofig.javaquest.map.object.dron;

import apofig.compiler.JavaCompiler;
import apofig.compiler.JavaMethod;
import apofig.javaquest.map.Action;
import apofig.javaquest.map.Player;
import apofig.javaquest.map.object.*;
import apofig.javaquest.services.Tickable;

/**
 * User: sanja
 * Date: 01.09.13
 * Time: 2:25
 */
public class Dron extends TalkingObject implements Something, Tickable {

    public static final char CHAR = '*';
    private Me hero;

    private String code =
            "public String whereToGo(String nearMe) {\n" +
            "    return \"|\";\n" +
            "}";

    private boolean active = false;

    @Override
    public void answer(String message) {
        this.code = message;
        sayOnce("Команда принята! Обработка начнется после того как ты отойдешь.");
    }

    @Override
    public boolean iCanLeave() {
        return true;
    }

    @Override
    public void askMe() {
        sayOnce("Я твой робот! Запрограммируй меня.");
        active = false;
    }

    @Override
    public boolean iCanUse() {
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
        sayOnce("Обработка началась!");
        active = true;

    }

    @Override
    public void onKill(Action action) {
        // do nothing
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void tick() {
        if (active) {
            Place place = getPlace();

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
                sayOnce("Команда '" + whereToGo + "' не принята! Остановка!!!");
                return;
            }

            char atWay = place.near(dx, dy);
            if (atWay == '$') {
                say("Дрон подобрал золото!");
                hero.getInfo().addGold(10); // TODO тут хардкод, это должно решать золото а не дрон
            }
            if (atWay == ' ' || atWay == '$') {
                move(place.getX() + dx, place.getY() + dy);
            } else {
                active = false;
                sayOnce("Дрон уперся в неопознанный объект! Остановка!!!");
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
        hero.subscribe(this);
    }
}
