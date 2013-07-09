package apofig.javaquest.web;

import apofig.javaquest.map.JavaQuestSinglePlayer;
import apofig.javaquest.map.Joystick;
import apofig.javaquest.map.Player;
import apofig.javaquest.services.PlayerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * User: oleksandr.baglai
 * Date: 1/30/13
 * Time: 3:21 PM
 */
@Controller
public class GameController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String post(Model model, HttpSession session) {
        return printGame(model, session);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView command(Model model, HttpSession session, @RequestParam String command) throws JSONException {
        JavaQuestSinglePlayer game = getGameFrom(session);
        Joystick joystick = game.getJoystick();
        if (command.equals("up")) {
            joystick.moveUp();
        } else if (command.equals("down")) {
            joystick.moveDown();
        } else if (command.equals("left")) {
            joystick.moveLeft();
        } else if (command.equals("right")) {
            joystick.moveRight();
        } else if (command.equals("refresh")) {
            // do nothing
        } else {
            joystick.attack(command.replaceAll("</br>", "\n"));
        }

        JSONObject result = new JSONObject();
        result.put("map", getMap(game));
        result.put("message", game.getMessage());
        result.put("code", game.getCodeHelper().getCode());
        result.put("info", game.getPlayerInfo());
        String json = result.toString();

        ModelAndView mav = new ModelAndView("html_utf8");
        mav.addObject("responseBody", json);
        return mav;
    }

    private String printGame(Model model, HttpSession session) {
        JavaQuestSinglePlayer game = getGameFrom(session);
        return print(model, getMap(game), game.getPlayerInfo());
    }

    private String getMap(JavaQuestSinglePlayer game) {
        return Colorizer.process(game.toString());
    }

    private String print(Model model, String map, Player playerInfo) {
        model.addAttribute("map", map);
        model.addAttribute("message", "");
        model.addAttribute("info", playerInfo.toString());
        return "game";
    }

    private JavaQuestSinglePlayer getGameFrom(HttpSession session) {
        JavaQuestSinglePlayer game = (JavaQuestSinglePlayer)session.getAttribute("game");
        if (game == null) {
            game = playerService.newGame();
            session.setAttribute("game", game);
        }
        return game;
    }

}
