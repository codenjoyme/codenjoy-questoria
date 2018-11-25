package apofig.javaquest.field;

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
public class LoadFieldFromFile implements FieldLoader {

    private Field field;
    private int posx;
    private int posy;

    public LoadFieldFromFile(String fileName) {
        List<String> lines = loadFromFile(fileName);

        int height = lines.get(0).length();
        int width = lines.size();

        field = new Field(height, width);

        for (int y = 0; y < width; y++) {
            String line = lines.get(y);
            int dy = width - 1 - y;
            for (int x = 0; x < line.length(); x++) {
                field.set(x, dy, line.charAt(x));

                if (field.get(x, dy) == 'I') {
                    posx = x;
                    posy = dy;
                    field.set(x, dy, ' ');
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
        List<String> lines = new LinkedList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(uri));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (IOException e) {
            scanner.close();
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public int width() {
        return field.getWidth();
    }

    @Override
    public int height() {
        return field.getHeight();
    }

    @Override
    public Field field() {
        return field;
    }

    @Override
    public Point initPosition() {
        return new PointImpl(posx, posy);
    }
}
