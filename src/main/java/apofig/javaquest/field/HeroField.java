package apofig.javaquest.field;

public interface HeroField {

    void openSpace(Viewable me);

    String getViewArea(Viewable me);

    FieldPlace newHero(Viewable me);

    void removeHero(Viewable me);
}
