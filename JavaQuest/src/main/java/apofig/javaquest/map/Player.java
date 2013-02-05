package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:38 AM
 */
public class Player implements PlayerInfo {

    private int gold;

    public String toString() {
        return String.format("Level:%s Xp:%s Health:%s Gold:%s", 0, 0, 100, gold);
    }

    public void addGold(int count) {
        gold += count;
    }
}