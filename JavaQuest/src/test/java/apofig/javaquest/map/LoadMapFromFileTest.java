package apofig.javaquest.map;

import org.approvaltests.Approvals;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:37 PM
 */
public class LoadMapFromFileTest {

    @Test
    public void shouldLoadFileToMap() throws Exception {
        MapLoader loader = new LoadMapFromFile("apofig\\javaquest\\map\\test_map.txt");

        assertEquals(33, loader.getHeight());
        assertEquals(35, loader.getWidth());
        assertEquals(10, loader.getPlayerX());
        assertEquals(24, loader.getPlayerY()); // TODO
        verifyMap(loader.getMap());
    }

    @Test
    public void shouldLoadFileToFog() throws Exception {
        MapLoader loader = new LoadMapFromFile("apofig\\javaquest\\map\\test_map.txt");

        verifyMap(loader.getFog());
    }

    private void verifyMap(char[][] map) throws Exception {
        int width = map.length;
        int height = map[0].length;

        final StringBuffer result = new StringBuffer();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                result.append(map[x][y]);
                result.append(" ");
            }
            result.append('\n');
        }

        Approvals.verify(result.toString());
    }
}
