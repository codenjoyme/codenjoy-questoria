package apofig.javaquest.map;

import com.sun.webpane.sg.prism.WCMediaPlayerImpl;
import org.approvaltests.Approvals;
import org.junit.Test;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:08 PM
 */
public class TestTerritoryMap {

    private JavaQuest game = new JavaQuest();

    @Test
    public void testIAmOnMap() throws Exception {
        TerritoryMap map = game.getTerritoryMap();

        verifyMap(map);
    }

    private void verifyMap(TerritoryMap map) throws Exception {
        StringOutputStream out = new StringOutputStream();
        map.printNearMe(out);
        Approvals.verify(out.getResult());
    }

    @Test
    public void testIGoRight() throws Exception {
        Joystick joystick = game.getPlayer();
        TerritoryMap map = game.getTerritoryMap();

        joystick.moveRight();

        verifyMap(map);
    }


}
