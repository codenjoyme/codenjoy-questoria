package com.codenjoy.dojo.questoria.model.items;

public interface Leaveable {

    default boolean canLeave(Me hero) {
        return true;
    }

    default void tryToLeave(Me hero) {
        leaved(hero);
    }

    default void leaved(Me hero) {
        // do nothing
    }
}
