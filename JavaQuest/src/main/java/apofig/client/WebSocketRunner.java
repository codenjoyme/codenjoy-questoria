package apofig.client;

import apofig.javaquest.transport.Socket;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WebSocketRunner {

    public static void run(String server, String portal, Solver solver) {
        try {
            WebSocketClient client = new WebSocketClient();
            client.start();
            client.connect(new ClientSocket(solver),
                    new URI("ws://" + server + "/java-quest/ws?portal=" + portal))
                    .get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @WebSocket
    public static class ClientSocket implements Socket {
        private Session session;
        private Solver solver;

        public ClientSocket(Solver solver) {
            this.solver = solver;
        }

        @Override
        @OnWebSocketConnect
        public void onConnect(Session session){
            this.session = session;
            System.out.println("Portal opened");
        }

        @Override
        @OnWebSocketMessage
        public void onText(Session session, String message) throws IOException {
            try {
                message = message.replaceAll("[\\[\\]]", "");
                String[] parameters = message.split(",");
                String answer = solver.getAnswer(parameters);
                session.getRemote().sendString(answer);
            } catch (IOException e) {
                System.out.println("Exception: " + e.toString());
            }
        }

        @Override
        @OnWebSocketClose
        public void onClose(Session session, int status, String reason){
            System.out.println("Portal closed");
            System.exit(0);
        }

        @Override
        @OnWebSocketError
        public void onError(Throwable error){
            System.out.println("Exception: " + error.toString());
        }
    }
}
