package apofig.javaquest.services;

import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.PortalMessenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component("heroService")
public class HeroServiceImpl implements HeroService {

    @Autowired
    protected PlayerService players;

    @Override
    public List<Me> getHeroes() {
        return players.players().stream()
                .map(Player::getGame)
                .map(game -> (Me)(game.getJoystick()))
                .collect(toList());
    }

    @Override
    public PortalMessenger getPortalMessenger(String portalCode) {
        Me found = getHeroes().stream()
                .filter(hero -> hero.getInfo().getPortalCode().equals(portalCode))
                .findFirst()
                .orElse(null);

        if (found == null) {
            return null;
        }
        return (PortalMessenger) found.getPortal().getMonster();
    }
}
