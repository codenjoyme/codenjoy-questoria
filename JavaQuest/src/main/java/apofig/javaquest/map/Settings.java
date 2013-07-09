package apofig.javaquest.map;

import apofig.javaquest.map.object.monster.MonsterPool;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:13 PM
 */
public interface Settings {

    int viewSize();

    MapLoader mapLoader();

    MonsterPool monsters();
}
