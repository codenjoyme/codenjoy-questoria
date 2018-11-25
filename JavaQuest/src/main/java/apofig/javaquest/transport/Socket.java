package apofig.javaquest.transport;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import java.io.IOException;

public interface Socket {
    @OnWebSocketConnect
    void onConnect(Session session);

    @OnWebSocketMessage
    void onText(Session session, String message) throws IOException;

    @OnWebSocketClose
    void onClose(Session session, int status, String reason);

    @OnWebSocketError
    void onError(Throwable error);
}
