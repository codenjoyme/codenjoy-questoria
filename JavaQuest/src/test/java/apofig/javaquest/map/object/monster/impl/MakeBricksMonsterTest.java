package apofig.javaquest.map.object.monster.impl;

import org.approvaltests.legacycode.LegacyApprovals;
import org.approvaltests.legacycode.Range;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: oleksandr.baglai
 * Date: 2/26/13
 * Time: 11:42 PM
 */
public class MakeBricksMonsterTest {

    private static final int HHHHH = 5;
    private static final int H = 1;
    public static final boolean NO = false;
    public static final boolean YES = true;

    @Test
    public void shouldWork() throws Exception {
        YES(5, HHHHH);
        YES(1, H);
        NO(2, H);
        YES(7, HHHHH, H, H, H);
        YES(14, HHHHH, HHHHH, H, H, H, H);
        NO(14, HHHHH, HHHHH,  H, H, H);
        YES(5, HHHHH, HHHHH);
        YES(6, HHHHH, H, H, H);
        NO(9, HHHHH, H, H, H);
        NO(9, HHHHH, HHHHH, H, H, H);
        NO(1, HHHHH);
        NO(1, HHHHH);
        NO(2, HHHHH, H);
        NO(3, HHHHH, H, H);
        NO(4, HHHHH, H, H, H);
        YES(1, HHHHH, H, H);
        YES(6, HHHHH, HHHHH, H, H);
        YES(11, HHHHH, H, H, H, H, H, H);
        YES(1, H, H, H, H, H);

        LegacyApprovals.LockDown(this, "method", Range.get(0, 10), Range.get(0, 10), Range.get(0, 100));
    }

    private void YES(int l, int... bricks) {
        assertBricks(YES, l, bricks);
    }

    private void NO(int l, int... bricks) {
        assertBricks(NO, l, bricks);
    }

    private void assertBricks(boolean expected, int l, int... bricks) {
        int c1 = 0;
        int c5 = 0;
        for (int i : bricks) {
            if (i == H) {
                c1++;
            }
            if (i == HHHHH) {
                c5++;
            }
        }
        assertL(expected, c1, c5, l);
    }

    private void assertL(boolean expected, int c1, int c5, int l) {
        boolean actual = Boolean.valueOf(method(c1, c5, l));
        assertEquals(String.format("C1 = %s, C5 = %s, L = %s, Actual = %s, But expected = True", c1, c5, l, actual, expected),
                expected, actual);
    }

    public String method(Integer small, Integer big, Integer goal) {
        return new MakeBricksMonster().method(small, big, goal);
    }
}
