package apofig.javaquest.map;

import apofig.javaquest.map.object.monster.MonsterFactory;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:13 PM
 */
public interface Settings {

    int getViewAreaSize();

    MapLoader getMapLoader();

    MonsterFactory getMonsters();
}
