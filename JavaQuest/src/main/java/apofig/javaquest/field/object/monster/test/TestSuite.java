package apofig.javaquest.field.object.monster.test;

import apofig.javaquest.field.Messages;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestSuite {

    private List<TestResult> tests = new LinkedList<>();

    public String getResults() {
        if (isGreenLine()) {
            return "OK";
        }

        return collectTestMessages();
    }

    private String collectTestMessages() {
        List<String> messages =
                tests.stream()
                        .map(test -> test.message())
                        .collect(toList());
        return Messages.join(messages, "\n");
    }

    public boolean isGreenLine() {
        return !tests.stream()
                .filter(TestResult::fail)
                .findAny()
                .isPresent();
    }

    private boolean isErrorPresent() {
        return tests.stream()
                .filter(TestResult::error)
                .findAny()
                .isPresent();
    }

    public void add(TestResult testResult) {
        tests.add(testResult);
    }

    public boolean isEnough() {
        if (isGreenLine()) {
            return false;
        }

        if (isErrorPresent()) {
            return true;
        }
        if (countFailed() >= 6) {
            return true;
        }
        return false;
    }

    private long countFailed() {
        return tests.stream()
                .filter(TestResult::fail)
                .count();
    }

    public void clear() {
        tests.clear();
    }
}
