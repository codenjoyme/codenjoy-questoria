package apofig.javaquest.map;

import apofig.javaquest.map.object.Map;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:13 PM
 */
public class LoadMapFromFile implements MapLoader {

    private Map map;
    private Map fog;
    private int posx;
    private int posy;

    public LoadMapFromFile(String fileName) {
        List<String> lines = loadFromFile(fileName);

        int height = lines.get(0).length();
        int width = lines.size();

        map = new Map(height, width);
        fog = new Map(height, width, '?');

        for (int y = 0; y < width; y++) {
            String line = lines.get(y);
            int dy = width - 1 - y;
            for (int x = 0; x < line.length(); x++) {
                map.set(x, dy, line.charAt(x));

                if (map.get(x, dy) == 'I') {
                    posx = x;
                    posy = dy;
                    map.set(x, dy, ' ');
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
        try (Scanner scanner = new Scanner(new File(uri))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public int getWidth() {
        return map.getWidth();
    }

    @Override
    public int getHeight() {
        return map.getHeight();
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public Map getFog() {
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
