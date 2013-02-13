package apofig.javaquest.map.object.monster;

/**
 * User: oleksandr.baglai
 * Date: 2/5/13
 * Time: 9:56 PM
 */
public class MonsterFactoryImpl implements MonsterFactory {

    public Monster next() {
        return new FizzBuzzMonster(null);
    }
}
