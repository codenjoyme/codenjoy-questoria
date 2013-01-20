package apofig.javaquest.map;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 8:30 PM
 */
public class Monster implements Something {

    private final OnKill onKill;

    public Monster(OnKill onKill) {
        this.onKill = onKill;
    }

    @Override
    public String answer(String message) {
        if (message.equals("die!")) {
            onKill.doit();
            return "Monster: yOU @#& Ki$%@&^ll me $!@!";
        }
        return "Monster: I'll kill you!";
    }

    @Override
    public boolean iCanLeave() {
        return false;
    }

    @Override
    public String askMe() {
        return "Monster: Fight with me!";
    }

    @Override
    public boolean iCanUse() {
        return false;
    }
}
