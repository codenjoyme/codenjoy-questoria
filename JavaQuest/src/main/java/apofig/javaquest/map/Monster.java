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
    public String say(String message) {
        if (message.equals("die!")) {
            onKill.doit();
            return "yOU @#& Ki$%@&^ll me $!@!";
        }
        return "I'll kill you!";
    }
}
