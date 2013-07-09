package apofig.javaquest.map;

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
        MapLoader loader = new LoadMapFromFile("test_map.txt");

        assertEquals(33, loader.getHeight());
        assertEquals(35, loader.getWidth());
        assertEquals(10, loader.getPlayerX());
        assertEquals(8, loader.getPlayerY());
        verifyMap(loader.getMap(),
                "###################################\n" +
                "#################      ############\n" +
                "######              @  ############\n" +
                "######                #############\n" +
                "####    @   #######################\n" +
                "#####        ######################\n" +
                "######                      #######\n" +
                "#######                     #######\n" +
                "#######      @              #######\n" +
                "#######                 ###########\n" +
                "###                  ##############\n" +
                "###########           #############\n" +
                "############                #######\n" +
                "##########                 ########\n" +
                "#################         #########\n" +
                "####################    @   #######\n" +
                "#####################        ######\n" +
                "###################        ########\n" +
                "##################         ########\n" +
                "###################       #########\n" +
                "#####################     #########\n" +
                "#################        ##########\n" +
                "###############      ##############\n" +
                "##########          ###############\n" +
                "#######           #################\n" +
                "####          #####################\n" +
                "########                  #########\n" +
                "#########                   #######\n" +
                "############             ##########\n" +
                "##############          ###########\n" +
                "#################      ############\n" +
                "###################################\n" +
                "###################################\n");
    }

    @Test
    public void shouldLoadFileToMap2() throws Exception {
        MapLoader loader = new LoadMapFromFile("test_map2.txt");

        assertEquals(12, loader.getHeight());
        assertEquals(50, loader.getWidth());
        assertEquals(2, loader.getPlayerX());
        assertEquals(8, loader.getPlayerY());
        verifyMap(loader.getMap(),
                "##################################################\n" +
                "##################################################\n" +
                "##################################################\n" +
                "##             @                 @             ###\n" +
                "##############################################@###\n" +
                "####         @               @                 ###\n" +
                "####@#############################################\n" +
                "####       @             @              @      ###\n" +
                "##############################################@###\n" +
                "########      @                @               ###\n" +
                "########@#########################################\n" +
                "##################################################\n");
    }

    private void verifyMap(Map map, String expected) throws Exception {
        int width = map.getWidth();
        int height = map.getHeight();

        final StringBuffer result = new StringBuffer();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                result.append(map.get(x, y));
            }
            result.append('\n');
        }

        assertEquals(expected, result.toString());
    }
}
