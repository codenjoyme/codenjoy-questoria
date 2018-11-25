package apofig.javaquest.field;

/**
 * User: sanja
 * Date: 29.09.13
 * Time: 20:39
 */
public interface HeroField {

    void openSpace(Viewable me);

    String getViewArea(Viewable me);

    FieldPlace newHero(Viewable me);

    void removeHero(Viewable me);
}
