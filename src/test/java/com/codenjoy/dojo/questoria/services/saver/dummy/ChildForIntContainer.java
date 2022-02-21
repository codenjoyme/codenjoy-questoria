package com.codenjoy.dojo.questoria.services.saver.dummy;

public class ChildForIntContainer extends IntContainer {
    private int b;

    private ChildForIntContainer() {
        super(0);
    }

    public ChildForIntContainer(int a, int b) {
        super(a);
        this.b = b;
    }
}
