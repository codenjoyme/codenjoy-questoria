package com.codenjoy.dojo.questoria.model.items.monster;

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

import com.codenjoy.dojo.questoria.model.items.Me;
import com.codenjoy.dojo.questoria.model.items.PortalMessenger;
import com.codenjoy.dojo.questoria.model.items.monster.test.TestResult;
import com.codenjoy.dojo.services.Tickable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class WSCodeRunnerMonster extends CodeRunnerMonster implements PortalMessenger, Tickable {

//    private PlayerSocket socket;
    private List<Object[]> testList = new LinkedList<>();
    private SendStatus send = SendStatus.NONE;
    private Object[] currentTest;

    enum SendStatus {
        NONE, SENT, REPEAT, ANSWERED, FINISHED;

        SendStatus() {
            // do nothing
        }
    }

    public WSCodeRunnerMonster(String question, String signature, int weight) {
        super(question, signature, weight);
    }

    @Override
    public void tick() {
//        if (socket == null) {
//            System.out.println("socket is null");
//            return;
//        }
//
//        if (socket.isClosed()) {
//            System.out.println("socket is closed");
//            socket = null;
//            clear();
//            return;
//        }

        if (testList.isEmpty() && send == SendStatus.FINISHED) {
            System.out.println("testing complete!");
            if (testSuite.isGreenLine()) {
                System.out.println("all test complete with green line");
                disconnect();
                super.answer("4E0A");
            } else {
                System.out.println("all test complete with failures");
                messenger.say(help);
                disconnect();
            }
            return;
        }

        if (testList.isEmpty() && send == SendStatus.NONE) {
            System.out.println("start testing...");
            testList.addAll(Arrays.asList(getTestData()));
        }

        if (send != SendStatus.SENT) {
            if (send != SendStatus.REPEAT) {
                currentTest = testList.remove(0);
            }
            send = SendStatus.SENT;
            System.out.println("send question: " + Arrays.toString(currentTest));
//            socket.callMethod(currentTest);
            return;
        }

        String answer = null; //socket.popAnswer();
        if (answer == null) {
            System.out.println("answer is null");
            send = SendStatus.REPEAT;
            disconnect(); // TODO пробовать отправлять вопрос n-ное количество раз, а потом отрубать связь
            return;
        }

        System.out.println("got answer: " + answer);
        send = SendStatus.ANSWERED;
        // TODO не использовать тут лямбды потому что иначе MonsterLoader не найдет наши головоломки
        TestResult testResult = test.test(currentTest, new MethodRunner() {
            @Override
            public Object run(Object... objects) {
                return answer;
            }
        });
        testSuite.add(testResult);
        messenger.say(testResult.message());

        if (testSuite.isEnough()) {
            System.out.println("there are failures - stop testing");
            messenger.say(help);
            disconnect();
            return;
        }

        if (testList.isEmpty()) {
            send = SendStatus.FINISHED;
        } else {
            tick();
        }
    }

    private void disconnect() {
        System.out.println("disconnected");
        clear();
//        if (socket != null) {
//            socket.close();
//            socket = null;
//        }
    }

    private void clear() {
        System.out.println("clearing suite...");
        if (send == SendStatus.NONE) {
            return;
        }
        send = SendStatus.NONE;
        testList.clear();
        testSuite.clear();

        messenger.say(String.format("Портал %s разрушен", getPortal()));
        System.out.println("cleared!");
    }

    private String getPortal() {
        if (hero == null || hero.getPortal() == null) {
            return null;
        }
        return hero.getPortal().getHash();
    }

    @Override
    public void portalCreated(Object socket) {
//        this.socket = socket;

        messenger.say(String.format("Портал %s открыт", getPortal()));

        // TODO не использовать тут лямбды потому что иначе MonsterLoader не найдет наши головоломки
//        socket.setOnClose(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("client closed()");
//                clear();
//            }
//        });
    }

    @Override
    public void leaved(Me hero) {
        super.leaved(hero);

        System.out.println("leaved(Me hero)");
        disconnect();
    }
}
