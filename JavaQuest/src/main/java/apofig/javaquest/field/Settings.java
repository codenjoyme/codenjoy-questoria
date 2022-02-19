package apofig.javaquest.field;

import apofig.javaquest.field.object.monster.MonsterFactory;

public interface Settings {

    int viewSize();

    FieldLoader fieldLoader();

    MonsterFactory monsters();
}
