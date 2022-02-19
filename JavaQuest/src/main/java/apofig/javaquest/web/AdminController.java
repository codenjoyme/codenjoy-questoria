package apofig.javaquest.web;

import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin31415")
public class AdminController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAdminPage(HttpServletRequest request) {
        request.setAttribute("players", playerService.players());
        return "admin";
    }

    @RequestMapping(params = "save", method = RequestMethod.GET)
    public String saveGame(HttpServletRequest request) {
       playerService.saveAllGame();
        return getAdminPage(request);
    }

    @RequestMapping(params = "load", method = RequestMethod.GET)
    public String loadGame(HttpServletRequest request) {
        playerService.loadAllGame();
        return getAdminPage(request);
    }

    @RequestMapping(params = "remove", method = RequestMethod.GET)
    public String removePlayer(HttpServletRequest request, @RequestParam("remove") String playerName) {
        playerService.remove(playerName);
        return getAdminPage(request);
    }
}
