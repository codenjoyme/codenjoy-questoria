package apofig.javaquest.web;

import apofig.javaquest.map.JavaQuestSinglePlayer;
import apofig.javaquest.map.Joystick;
import apofig.javaquest.services.Player;
import apofig.javaquest.services.PlayerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public String home() {
        return "redirect:/register";
    }

    @RequestMapping(value = "/user/{playerGameCode}", method = RequestMethod.GET)
    public String loadPlayerGame(Model model, @PathVariable("playerGameCode") String playerGameCode) {
        Player player = playerService.loadGame(playerGameCode);
        if (player == null) {
            return "redirect:/register";
        } else {
            model.addAttribute("map", getMap(player.getGame()));
            model.addAttribute("message", "");
            model.addAttribute("info", player.getGame().getPlayerInfo().toString());
            model.addAttribute("playerGameCode", playerGameCode);

            return "game";
        }
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView command(@RequestParam String command, @RequestParam String playerGameCode) throws JSONException {
        JavaQuestSinglePlayer game = playerService.loadGame(playerGameCode).getGame();
        if (game == null) {
            buildResponse("{error:'Неверный код игры - такого пользователя нет!'}");
        }

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

        return buildResponse(json);
    }

    private ModelAndView buildResponse(String json) {
        ModelAndView result = new ModelAndView("html_utf8");
        result.addObject("responseBody", json);
        return result;
    }

    private String getMap(JavaQuestSinglePlayer game) {
        return Colorizer.process(game.toString());
    }

}
