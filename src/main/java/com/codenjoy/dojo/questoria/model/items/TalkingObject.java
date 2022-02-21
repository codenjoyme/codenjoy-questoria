package com.codenjoy.dojo.questoria.model.items;

public abstract class TalkingObject implements SetMessenger, Askable {

    protected Messenger messenger;

    public Messenger getMessenger() {
        return messenger;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
        this.messenger.changeName(getName());
    }

    public void connect(TalkingObject object) {
        getMessenger().add(object.messenger.getMessages());
    }

    public void disconnect(TalkingObject object) {
        getMessenger().remove(object.messenger.getMessages());
    }
}
