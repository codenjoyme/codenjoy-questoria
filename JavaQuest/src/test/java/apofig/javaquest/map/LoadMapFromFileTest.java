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

        assertEquals(33, loader.height());
        assertEquals(35, loader.width());
        assertEquals("[10,8]", loader.initPosition().toString());
        verifyMap(loader.map(),
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

        assertEquals(12, loader.height());
        assertEquals(50, loader.width());
        assertEquals("[2,8]", loader.initPosition().toString());
        verifyMap(loader.map(),
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
