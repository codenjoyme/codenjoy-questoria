package apofig.javaquest.field;

public class Player implements PlayerInfo {

    private int gold;
    private String name;
    private String portalCode;

    private Player() {}

    public Player(String name) {
        this.name = name;
    }

    public String toString() {
        String portalPart = String.format(" Портал: %s", portalCode);

        return String.format("Уровень:%s Опыт:%s Здоровье:%s Золото:%s%s",
                0, 0, 100, gold, (portalCode == null) ? "" : portalPart);
    }

    public void addGold(int count) {
        gold += count;
    }

    public int filchGold(int count) {
        if (gold < count) {
            int old = gold;
            gold = 0;
            return old;
        } else {
            gold -= count;
            return count;
        }
    }

    public String getName() {
        return name;
    }

    public void setPortalCode(String portal) {
        this.portalCode = portal;
    }

    public String getPortalCode() {
        return portalCode;
    }
}
