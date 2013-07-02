package apofig.javaquest.web;

import apofig.javaquest.map.*;
import apofig.javaquest.services.PlayerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
         return printGame(model);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView command(Model model, HttpSession session, @RequestParam String command) throws JSONException {
        JavaQuest game = playerService.getGame();
        Joystick joystick = game.getPlayer();
        if (command.equals("up")) {
            joystick.moveUp();
        } else if (command.equals("down")) {
            joystick.moveDown();
        } else if (command.equals("left")) {
            joystick.moveLeft();
        } else if (command.equals("right")) {
            joystick.moveRight();
        } else if (command.equals("refresh")) {
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

    private String printGame(Model model) {
        JavaQuest game = playerService.getGame();
        return print(model, getMap(game), game.getPlayerInfo());
    }

    private String getMap(JavaQuest game) {
        return Colorizer.process(game.getTerritoryMap().getViewArea());
    }

    private String print(Model model, String map, Player playerInfo) {
        model.addAttribute("map", map);
        model.addAttribute("message", "");
        model.addAttribute("info", playerInfo.toString());
        return "game";
    }

}
