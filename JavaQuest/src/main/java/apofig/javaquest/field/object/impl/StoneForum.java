package apofig.javaquest.field.object.impl;

import apofig.javaquest.field.object.Leaveable;
import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.Something;
import apofig.javaquest.field.object.TalkingObject;
import apofig.javaquest.field.object.monster.SimpleIterator;

import java.util.LinkedList;
import java.util.List;

public class StoneForum extends TalkingObject implements Something, Leaveable {

    public static final String MESSAGE_INTRO = "На камне кто-то что-то написал (нажми say чтобы посмотреть...)";

    public static final String MESSAGE_LAST = "Ты тоже можешь что-то написать тут...";

    private boolean canWrite = false;
    private List<String> messages = new LinkedList<String>();
    private SimpleIterator<String> iterator = new SimpleIterator<String>(messages);

    @Override
    public void answer(String message) {
        if (canWrite) {
            messages.add(message);
            canWrite = false;
            return;
        }
        if (iterator.hasNext()) {
            messenger.sayOnce(iterator.next());
        } else {
            messenger.sayOnce(MESSAGE_LAST);
            canWrite = true;
        }
    }

    @Override
    public boolean canLeave(Me hero) {
        return true;
    }

    @Override
    public void ask() {
        iterator.reset();
        canWrite = false;
        messenger.sayOnce(MESSAGE_INTRO);
    }

    @Override
    public Something leaveAfter() {
        return new Nothing();
    }

    @Override
    public char symbol() {
        return '0';
    }

    @Override
    public void tryToLeave(Me hero) {
        iterator.reset(messages);
        canWrite = false;
    }
}
