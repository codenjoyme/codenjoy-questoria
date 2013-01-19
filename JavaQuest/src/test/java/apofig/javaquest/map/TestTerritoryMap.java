package apofig.javaquest.map;

import com.sun.webpane.sg.prism.WCMediaPlayerImpl;
import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Test;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:08 PM
 */
public class TestTerritoryMap {

    private JavaQuest game;
    private TerritoryMap map;
    private Joystick joystick;

    @Before
    public void init() {
        game = new JavaQuest();
        map = game.getTerritoryMap();
        joystick = game.getPlayer();
    }

    @Test
    public void testIAmOnMap() throws Exception {
        verifyMap(map);
    }

    private void verifyMap(TerritoryMap map) throws Exception {
        StringOutputStream out = new StringOutputStream();
        map.printNearMe(out);
        Approvals.verify(out.getResult());
    }

    @Test
    public void testIGoRight() throws Exception {
        joystick.moveRight();

        verifyMap(map);
    }

    @Test
    public void testIGoLeft() throws Exception {
        joystick.moveLeft();

        verifyMap(map);
    }

    @Test
    public void testIGoUp() throws Exception {
        joystick.moveUp();

        verifyMap(map);
    }


}
