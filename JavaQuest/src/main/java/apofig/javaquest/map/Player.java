package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/31/13
 * Time: 1:38 AM
 */
public class Player implements PlayerInfo {

    private int gold;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("Уровень:%s Опыт:%s Здоровье:%s Золото:%s", 0, 0, 100, gold);
    }

    public void addGold(int count) {
        gold += count;
    }

    public String getName() {
        return name;
    }
}
