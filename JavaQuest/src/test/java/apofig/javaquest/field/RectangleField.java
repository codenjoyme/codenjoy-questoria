package apofig.javaquest.field;

public class RectangleField implements FieldLoader {

    private Field field;
    private int posx;
    private int posy;
    private int width;
    private int height;

    public RectangleField(int width, int height) {
        this.width = width;
        this.height = height;
        field = new Field(width, height, ' ');

        posx = 20;
        posy = 20;
    }

    public void setMonster(int x, int y) {
        field.set(x, y, '@');
    }

    public void setWall(int x, int y) {
        field.set(x, y, '#');
    }

    public void setDronMentor(int x, int y) {
        field.set(x, y, 'M');
    }

    public void setGold(int x, int y) {
        field.set(x, y, '$');
    }

    public void setStone(int x, int y) {
        field.set(x, y, 'O');
    }

    public void set(int x, int y, char c) {
        field.set(x, y, c);
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public Field field() {
        return field;
    }

    @Override
    public Point initPosition() {
        return new PointImpl(posx, posy);
    }

    @Override
    public int height() {
        return height;
    }
}
