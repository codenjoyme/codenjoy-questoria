package apofig.javaquest.map;

import java.util.Arrays;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:08 PM
 */
public class SquareMap implements MapLoader {

    private char[][] map;
    private char[][] fog;
    private int posx;
    private int posy;
    private int size;

    public SquareMap(int size) {
        this.size = size;
        map = new char[size][size];
        fog = new char[size][size];

        Utils.fill(fog, '?');
        Utils.fill(map, ' ');
        map[40][22] = '@';

        posx = 20;
        posy = 20;
    }

    @Override
    public int getMapSize() {
        return size;
    }

    @Override
    public char[][] getMap() {
        return map;
    }

    @Override
    public char[][] getFog() {
        return fog;
    }

    @Override
    public int getPlayerX() {
        return posx;
    }

    @Override
    public int getPlayerY() {
        return posy;
    }
}
