package apofig.javaquest.transport;

import apofig.javaquest.field.object.PortalMessenger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;
import java.util.Arrays;

@WebSocket
public class PlayerSocket implements Socket {

    private Session session;
    private String answer;
    private PortalMessenger portal;
    private Runnable onClose;

    public PlayerSocket(PortalMessenger portal) {
        this.portal = portal;
    }

    @Override
    @OnWebSocketConnect
    public void onConnect(Session session){
        this.session = session;
        portal.portalCreated(this);
    }

    @Override
    @OnWebSocketMessage
    public void onText(Session session, String message){
        answer = message;
    }

    @Override
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason){
        if (onClose != null) {
            onClose.run();
        }
    }

    @Override
    @OnWebSocketError
    public void onError(Throwable error){
        System.out.println("Error: " + error.toString());
    }

    public void callMethod(Object[] objects) {
        try {
            session.getRemote().sendString(Arrays.toString(objects));
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }

    public String popAnswer() {
        String answer = this.answer;
        this.answer = null;
        return answer;
    }

    public void close() {
        if (session.isOpen()) {
            session.close();
        }
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    public boolean isClosed() {
        return session == null || !session.isOpen();
    }
}
