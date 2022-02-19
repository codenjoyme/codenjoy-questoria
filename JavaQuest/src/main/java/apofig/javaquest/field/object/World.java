package apofig.javaquest.field.object;

public interface World {
    Place place();

    void move(int x, int y);

    Something make(char c, Object... params);

}
