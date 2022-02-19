package apofig.javaquest.field.object.monster;

import apofig.javaquest.field.Action;
import apofig.javaquest.field.object.Something;
import com.google.common.collect.Lists;

import java.util.List;

public class MonsterPoolImpl implements MonsterPool {

    private List<Monster> monsters;
    private Monster monster;
    private int count;

    private MonsterPoolImpl() {}

    public MonsterPoolImpl(Iterable<Monster> otherMonsters) {
        monsters = Lists.newLinkedList(otherMonsters);
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
                    CodeRunnerMonster.LEAVE,
                    "", count);
            count++;
        }
        monster.onKill(new OnKill());
        return monster;
    }

    class OnKill implements Action {

        private OnKill() {}

        @Override
        public void act(Something object) {
            monster = null;
        }
    }
}
