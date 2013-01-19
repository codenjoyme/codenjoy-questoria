package apofig.javaquest.map;

import org.approvaltests.Approvals;
import org.junit.Test;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:08 PM
 */
public class TestTerritoryMap {

    @Test
    public void testIAmOnMap() throws Exception {
        TerritoryMap map = new TerritoryMap();
        StringOutputStream out = new StringOutputStream();
        map.printNearMe(out);
        Approvals.verify(out.getResult());
    }


}
