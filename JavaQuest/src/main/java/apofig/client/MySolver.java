package apofig.client;

public class MySolver implements Solver {

    @Override
    public String getAnswer(String... parameters) {
        int i = Integer.parseInt(parameters[0]);

        String result = "";
        if (i % 3 == 0) {
            result += "Fizz";
        }
        if (i % 5 == 0) {
            result += "Buzz";
        }
        if (result.length() == 0) {
            result = String.valueOf(i);
        }
        return result;
    }

    public static void main(String[] args) {
        WebSocketRunner.run("127.0.0.1:8080", "413FDA17", new MySolver());
    }
}
