package apofig.javaquest.map;

import org.approvaltests.Approvals;
import org.junit.Test;

/**
 * User: oleksandr.baglai
 * Date: 1/20/13
 * Time: 6:37 PM
 */
public class LoadMapFromFileTest {

    @Test
    public void shouldLoadFileToMap() throws Exception {
        MapLoader loader = new LoadMapFromFile("apofig\\javaquest\\map\\test_map.txt");

        verifyMap(loader.getMap());
    }

    private void verifyMap(char[][] map) throws Exception {
        int size = map.length;

        final StringBuffer result = new StringBuffer();
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                result.append(map[x][y]);
                result.append(" ");
            }
            result.append('\n');
        }

        Approvals.verify(result.toString());
    }
}
