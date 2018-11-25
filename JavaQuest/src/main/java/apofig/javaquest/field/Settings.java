package apofig.javaquest.field;

import apofig.javaquest.field.object.monster.MonsterFactory;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:13 PM
 */
public interface Settings {

    int viewSize();

    FieldLoader fieldLoader();

    MonsterFactory monsters();
}
