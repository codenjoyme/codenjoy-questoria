package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.object.Place;
import apofig.javaquest.map.object.Something;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:56 PM
 */
public class MonsterFactoryImpl implements MonsterPool {

    private List<Monster> monsters;
    private Monster monster;
    private int count;

    // TODO автоматизировать загрузку классов монстров по маркер интерфейсу и выстроить их в порядке сложности
    public MonsterFactoryImpl() {
        monsters = new LinkedList<Monster>();
        monsters.add(new FizzBuzzMonster());
        monsters.add(new PrimeFactoryMonster());
        monsters.add(new FibonacciNumbersMonster());
        monsters.add(new SumSquareDifferenceMonster());
        monsters.add(new XthPrimeMonster());
        monsters.add(new PowerDigitSumMonster());
        monsters.add(new FactorialMonster());
        monsters.add(new LongDivisionMonster());
        monsters.add(new MakeBricksMonster());
        count = monsters.size() + 1;
    }

    public Monster next() {
        if (monster != null) {
            return monster;
        }

        if (!monsters.isEmpty()) {
            monster = monsters.remove(0);
        } else {
            monster = new Monster("Я монстр №" + count + "! Борись со мной!",
                    "die!",
                    "Я убью тебя!",
                    "Никуда ты не уйдешь!",
                    "");
            count++;
        }
        monster.onKill(new Action() {
            @Override
            public void act(Something object) {
                monster = null;
            }
        });
        return monster;
    }
}
