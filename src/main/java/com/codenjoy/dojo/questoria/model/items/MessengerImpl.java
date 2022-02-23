package com.codenjoy.dojo.questoria.model.items;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.questoria.model.Messages;

import java.util.LinkedList;

public class MessengerImpl implements Messenger {

    private LinkedList<Messages> messages;
    private String name;

    public MessengerImpl() {
        this.messages = new LinkedList<>();
    }

    @Override
    public void say(String message) {
        for (Messages m : messages) {
            m.add(buildMessage(message));
        }
    }

    private String buildMessage(String message) {
        return name + ": " + message;
    }

    @Override
    public void sayOnce(String message) {
        for (Messages m : messages) {
            m.addOnce(buildMessage(message));
        }
    }

    @Override
    public Messages getMessages() {
        return messages.getFirst();
    }

    @Override
    public void sayToLast(String message) {
        messages.getLast().add(buildMessage(message));
    }

    @Override
    public void add(Messages messages) {
        if (this.messages.contains(messages)) {
            return;
        }

        this.messages.add(messages);
    }

    @Override
    public void remove(Messages messages) {
        this.messages.remove(messages);
    }

    @Override
    public void changeName(String name) {
        this.name = name;
    }

}
