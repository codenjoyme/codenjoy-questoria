package apofig.javaquest.web;

import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/register")
public class RegistrationController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public String openRegistrationForm(HttpServletRequest request) {
        request.setAttribute("player", new Player());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitRegistrationForm(Player player, HttpServletRequest request) {
        if (playerService.alreadyRegistered(player.getName())) {
            request.setAttribute("busy", true);
        } else {
            String playerGameCode = playerService.register(player.getName());
            request.getSession().setAttribute("playerGameCode", playerGameCode);
            request.setAttribute("playerGameCode", playerGameCode);
            request.setAttribute("registered", true);
        }
        request.setAttribute("player", null);
        return "register";
    }
}
