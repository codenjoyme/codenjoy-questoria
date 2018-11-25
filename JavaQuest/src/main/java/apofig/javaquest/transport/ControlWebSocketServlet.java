package apofig.javaquest.transport;

import apofig.javaquest.services.HeroService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ControlWebSocketServlet extends WebSocketServlet {

    @Autowired
    private HeroService heroes;

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        PlayerSocketCreator creator = new PlayerSocketCreator(heroes);
        webSocketServletFactory.setCreator(creator);
    }
}
