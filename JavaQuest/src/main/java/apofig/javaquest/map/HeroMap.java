package apofig.javaquest.map;

import apofig.javaquest.map.object.Me;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 20:39
 */
public interface HeroMap {

    void openSpace(Viewable me);

    String getViewArea(Viewable me);

    MapPlace newHero(Viewable me);
}
