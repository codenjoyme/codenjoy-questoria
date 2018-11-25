package apofig.javaquest.field.object.monster.test;

import java.util.Arrays;

public class TestResult {

    private String message;
    private boolean success;
    private boolean error;

    public TestResult(Object[] test, Object actual, Object expected) {
        success = actual.equals(expected) || actual.toString().equals(expected.toString());
        if (success) {
            message = String.format("Для %s метод работает правильно - ты вернул “%s”",
                    Arrays.toString(test), actual);
        } else {
            message = String.format("Для %s метод должен вернуть “%s”, но ты вернул “%s”",
                    Arrays.toString(test), expected, actual);
        }
        error = false;
    }

    public TestResult(Object[] test, Exception exception) {
        message = String.format("Для %s метод сгенерировал Exception: %s",
                Arrays.toString(test), processException(exception));
        success = false;
        error = true;
    }

    public TestResult(Exception exception) {
        message = String.format("Exception: %s",
                processException(exception));
        success = false;
        error = true;
    }

    protected String processException(Exception exception) {
        if (exception.getClass().equals(NullPointerException.class)) {
            return "Извини, NPE!";
        }
        return exception.toString().replaceAll("Dynamic[0-9]+", "Dynamic"); // TODO hotfix
    }

    public String message() {
        return message;
    }

    public boolean success() {
        return success;
    }

    public boolean error() {
        return error;
    }

    public boolean fail() {
        return !success;
    }

    @Override
    public String toString() {
        return String.format("[Test: success=%s,error=%s,fail=%s,message=%s]",
                success(), error(), fail(), message());
    }
}
