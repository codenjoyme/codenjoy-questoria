package apofig.javaquest.transport;

import apofig.javaquest.field.object.PortalMessenger;
import apofig.javaquest.services.HeroService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PlayerSocketCreator implements WebSocketCreator {

    private HeroService heroes;

    public PlayerSocketCreator(HeroService heroes) {
        this.heroes = heroes;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletRequest, ServletUpgradeResponse response) {
        HttpServletRequest request = servletRequest.getHttpServletRequest();
        String authId = request.getParameter("portal");
        if (StringUtils.isEmpty(authId)) {
            return notFoundPortal(response);
        }
        PortalMessenger messenger = heroes.getPortalMessenger(authId);
        if (messenger == null) {
            return notFoundPortal(response);
        }
        return new PlayerSocket(messenger);
    }

    private Object notFoundPortal(ServletUpgradeResponse response) {
        try {
            // TODO хотелось бы это сообщение получить у клиента, а не какое-то абстрактное 'Error: org.eclipse.jetty.websocket.api.UpgradeException: Didn't switch protocols, expected status <101>, but got <401>'
            response.sendError(401, "Unauthorized access. Please use another PORTAL.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
