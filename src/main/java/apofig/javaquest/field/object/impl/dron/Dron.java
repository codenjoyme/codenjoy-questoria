package apofig.javaquest.field.object.impl.dron;

import apofig.javaquest.field.FieldLocator;
import apofig.javaquest.field.object.*;
import apofig.javaquest.field.object.impl.Nothing;
import apofig.javaquest.field.object.monster.CodeHelper;
import com.codenjoy.dojo.services.Tickable;

import java.util.List;

public class Dron extends TalkingObject implements Something, CodeHelper, Tickable, SetPlace, SetWorld, Leaveable, SetLocator {

    public static final char CHAR = '*';
    private Me hero;

    private String code =
            "public String whereToGo(String nearMe) {\n" +
            "    return \"|\";\n" +
            "}";

    private boolean active = false;

    private Place place;
    private World world;
    private FieldLocator locator;

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
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        messenger.sayOnce("Я твой робот! Запрограммируй меня.");
        active = false;
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
    public void tryToLeave(Me hero) {
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
            String whereToGo = runCode(code, getNear());   // TODO дрон может зависнуть, и вообще любой клиентский код может зависнуть
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

            Place atWay = place.near(dx, dy);
            if (atWay.getChar() == '$') {
                messenger.say("Дрон подобрал золото!");
                Usable gold = (Usable)locator.getAt(atWay, hero);
                gold.getBy(hero.getInfo());
            }
            if (atWay.getChar() == ' ' || atWay.getChar() == '$') {
                world.move(place.getX() + dx, place.getY() + dy);
            } else {
                active = false;
                messenger.sayOnce("Дрон уперся в неопознанный объект! Остановка!!!");
            }
        }
    }

    private String getNear() {
        List<Place> near = place.near();
        String nearString = "";
        for (Place p : near) {
            nearString += p.getChar();
        }
        return nearString;
    }

    private String runCode(String code, String near) {
        return code;
    }

    public void setHero(Me hero) {
        this.hero = hero;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void setLocator(FieldLocator locator) {
        this.locator = locator;
    }
}
