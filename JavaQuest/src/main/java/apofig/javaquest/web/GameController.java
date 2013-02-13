package apofig.javaquest.web;

import apofig.javaquest.map.*;
import apofig.javaquest.map.object.monster.MonsterPool;
import apofig.javaquest.map.object.monster.MonsterFactoryImpl;
import org.json.JSONException;
import org.json.JSONObject;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String post(Model model, HttpSession session) {
         return printGame(model, session);
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView command(Model model, HttpSession session, @RequestParam String command) throws JSONException {
        JavaQuest game = getGameFrom(session);
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
        result.put("message", getMessage(game));
        result.put("info", game.getPlayerInfo());
        String json = result.toString();

        ModelAndView mav = new ModelAndView("html_utf8");
        mav.addObject("responseBody", json);
        return mav;
    }

    private String printGame(Model model, HttpSession session) {
        JavaQuest game = getGameFrom(session);
        return print(model, getMap(game), game.getPlayerInfo());
    }

    private String getMap(JavaQuest game) {
        return Colorizer.process(game.getTerritoryMap().getViewArea());
    }

    private String encode(String string) {
        return string.replaceAll("\n", "<\\br>").replaceAll(" ", "&nbsp;");
    }

    private String getMessage(JavaQuest game) {
        return encode(game.getMessage());
    }

    private String print(Model model, String map, Player playerInfo) {
        model.addAttribute("map", map);
        model.addAttribute("message", "");
        model.addAttribute("info", playerInfo.toString());
        return "game";
    }

    private JavaQuest getGameFrom(HttpSession session) {
        JavaQuest game = (JavaQuest)session.getAttribute("getGameFrom");
        if (game == null) {
            Settings settings = new Settings() {
                @Override
                public int getViewAreaSize() {
                    return 41;
                }

                @Override
                public MapLoader getMapLoader() {
                    return new LoadMapFromFile("map.txt");
                }

                @Override
                public MonsterPool getMonsters() {
                    return new MonsterFactoryImpl();
                }
            };

            game = new JavaQuest(settings);
            session.setAttribute("getGameFrom", game);
        }
        return game;
    }

}
