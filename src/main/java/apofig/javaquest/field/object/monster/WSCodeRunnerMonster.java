package apofig.javaquest.field.object.monster;

import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.PortalMessenger;
import apofig.javaquest.field.object.monster.test.TestResult;
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
