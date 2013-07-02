package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.object.Something;

import java.util.LinkedList;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:56 PM
 */
public class MonsterFactoryImpl implements MonsterPool, Action {

    private List<Monster> monsters;
    private int count;
    private Monster monster;

    // TODO автоматизировать загрузку классов монстров по маркер интерфейсу и выстроить их в порядке сложности
    public MonsterFactoryImpl() {
        monsters = new LinkedList<Monster>();
        monsters.add(new FizzBuzzMonster(this));
        monsters.add(new PrimeFactoryMonster(this));
        monsters.add(new FibonacciNumbersMonster(this));
        monsters.add(new SumSquareDifferenceMonster(this));
        monsters.add(new XthPrimeMonster(this));
        monsters.add(new PowerDigitSumMonster(this));
        monsters.add(new FactorialMonster(this));
        monsters.add(new LongDivisionMonster(this));
        monsters.add(new MakeBricksMonster(this));
        count = monsters.size() + 1;
    }

    public Monster next() {
        if (monster == null) {
            monster = getNext();
        }
        return monster;
    }

    @Override
    public void act(Something object) {
        monster = getNext();
    }

    public Monster getNext() {
        if (monsters.size() != 0) {
            return monsters.remove(0);
        }
        return new Monster("Я монстр №" + count + "! Борись со мной!",
                "die!",
                "Я убью тебя!",
                "Никуда ты не уйдешь!",
                new Action() {
                    @Override
                    public void act(Something object) {
                        count++;
                        monster = getNext();
                    }
                });
    }
}
