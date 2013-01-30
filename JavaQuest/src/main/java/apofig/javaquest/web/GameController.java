package apofig.javaquest.web;

import apofig.javaquest.map.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * User: oleksandr.baglai
 * Date: 1/30/13
 * Time: 3:21 PM
 */
@Controller
@RequestMapping("/play")
public class GameController {

    @RequestMapping(method = RequestMethod.POST)
    public String get(Answer answer, BindingResult result, Model model, HttpSession session) {
        if (!result.hasErrors()) {
            getGameFrom(session).getPlayer().attack(answer.getMessage());
        }
        return printGame(model, session);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String post(Model model, HttpSession session) {
        return printGame(model, session);
    }

    @RequestMapping(params = "up", method = RequestMethod.GET)
    public String up(Model model, HttpSession session) {
        getGameFrom(session).getPlayer().moveUp();
        return printGame(model, session);
    }

    @RequestMapping(params = "down", method = RequestMethod.GET)
    public String down(Model model, HttpSession session) {
        getGameFrom(session).getPlayer().moveDown();
        return printGame(model, session);
    }

    @RequestMapping(params = "left", method = RequestMethod.GET)
    public String left(Model model, HttpSession session) {
        getGameFrom(session).getPlayer().moveLeft();
        return printGame(model, session);
    }

    @RequestMapping(params = "right", method = RequestMethod.GET)
    public String right(Model model, HttpSession session) {
        getGameFrom(session).getPlayer().moveRight();
        return printGame(model, session);
    }

    private String printGame(Model model, HttpSession session) {
        Answer answer = new Answer();
        model.addAttribute("answerForm", answer);

        String map = getGameFrom(session).getTerritoryMap().getViewArea().replaceAll("\n", "<\\br>").replaceAll(" ", "&nbsp;");
        return print(model, map);
    }

    private String print(Model model, String message) {
        model.addAttribute("message", message);
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
            };

            game = new JavaQuest(settings);
            session.setAttribute("getGameFrom", game);
        }
        return game;
    }

}
