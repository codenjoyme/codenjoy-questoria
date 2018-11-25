package apofig.javaquest.services;

import apofig.javaquest.field.object.Me;
import apofig.javaquest.field.object.PortalMessenger;

import java.util.List;

public interface HeroService {

    List<Me> getHeroes();

    PortalMessenger getPortalMessenger(String portalCode);
}
