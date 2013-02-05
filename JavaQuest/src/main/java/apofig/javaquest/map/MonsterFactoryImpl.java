package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:56 PM
 */
public class MonsterFactoryImpl implements MonsterFactory {
    private int count = 1;

    public Monster next() {
        return new Monster("I am " + count + "! Fight with me!", "die!", "I'll kill you!", new Action() {
            @Override
            public void act(Something object) {
                count++;
            }
        });
    }
}
