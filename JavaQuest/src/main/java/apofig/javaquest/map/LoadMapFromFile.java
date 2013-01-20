package apofig.javaquest.map;

import apofig.javaquest.map.MapLoader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:13 PM
 */
public class LoadMapFromFile implements MapLoader {

    private char[][] map;
    private char[][] fog;
    private int posx;
    private int posy;

    public LoadMapFromFile(String fileName) {
        List<String> lines = loadFromFile(fileName);

        map = new char[lines.get(0).length()][lines.size()];
        fog = new char[lines.get(0).length()][lines.size()];
        Utils.fill(fog, '?');

        for (int y = lines.size() - 1; y >= 0; y--) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                map[x][y] = line.charAt(x);

                if (map[x][y] == 'I') {
                    posx = x;
                    posy = y;
                }
            }
        }
    }

    private List<String> loadFromFile(String fileName) {
        URI uri = null;
        try {
            uri = getClass().getClassLoader().getResource(fileName).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<String> lines = new LinkedList<>();
        try (Scanner scanner = new Scanner(new File(uri))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public int getMapSize() {
        return map.length; // TODO
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
