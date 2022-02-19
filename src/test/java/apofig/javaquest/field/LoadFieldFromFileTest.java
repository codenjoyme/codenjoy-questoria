package apofig.javaquest.field;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LoadFieldFromFileTest {

    @Test
    public void shouldLoadFileToField() throws Exception {
        FieldLoader loader = new LoadFieldFromFile("test_field.txt");

        assertEquals(33, loader.height());
        assertEquals(35, loader.width());
        assertEquals("[10,8]", loader.initPosition().toString());
        verifyField(loader.field(),
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
    public void shouldLoadFileToField2() throws Exception {
        FieldLoader loader = new LoadFieldFromFile("test_field2.txt");

        assertEquals(12, loader.height());
        assertEquals(50, loader.width());
        assertEquals("[2,8]", loader.initPosition().toString());
        verifyField(loader.field(),
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

    private void verifyField(Field field, String expected) throws Exception {
        int width = field.getWidth();
        int height = field.getHeight();

        final StringBuffer result = new StringBuffer();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                result.append(field.get(x, y));
            }
            result.append('\n');
        }

        assertEquals(expected, result.toString());
    }
}
