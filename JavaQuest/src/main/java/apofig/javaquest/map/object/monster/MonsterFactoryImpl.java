package apofig.javaquest.map.object.monster;

import apofig.javaquest.map.Action;
import apofig.javaquest.map.object.Something;
import com.google.common.collect.Lists;

import java.util.Collections;
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

    private MonsterFactoryImpl() {}

    public MonsterFactoryImpl(Iterable<Monster> otherMonsters) {
        monsters = Lists.newArrayList(otherMonsters);
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
                    "", count);
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
