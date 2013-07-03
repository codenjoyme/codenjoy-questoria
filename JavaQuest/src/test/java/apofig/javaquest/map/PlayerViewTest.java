package apofig.javaquest.map;

import org.approvaltests.Approvals;
import org.junit.Test;

import java.util.Arrays;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 6:13 PM
 */
public class PlayerViewTest {


    private void fill(char[][] m, char с) {
        for (int x = 0; x < m.length; x++) {
            Arrays.fill(m[x], с);
        }
    }

    @Test
    public void testLargeView() throws Exception {
        verifyView(41);
    }

    @Test
    public void testSmallView() throws Exception {
        verifyView(11);
    }

    private void verifyView(int size) throws Exception {
        final int fogSize = size*2;
        final PlayerView view = new PlayerView(size);
        final StringBuffer result = new StringBuffer();

        view.see(fogSize/2, fogSize/2, fogSize/2, fogSize/2, fogSize, fogSize, new Apply() {
            @Override
            public void xy(int x, int y, boolean canSee, boolean isWall) {
                if (canSee) {
                    result.append(' ');
                } else {
                    result.append('?');
                }
                result.append(" ");

                boolean endLine = (x - fogSize/2) == view.radius();
                if (endLine) {
                    result.append('\n');
                }
            }
        });

        Approvals.verify(result.toString());
    }

}
