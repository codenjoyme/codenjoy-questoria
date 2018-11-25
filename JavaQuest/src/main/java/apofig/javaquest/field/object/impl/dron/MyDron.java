package apofig.javaquest.field.object.dron;

/**
 * User: sanja
 * Date: 01.09.13
 * Time: 2:35
 */
public class MyDron {

    public String whereToGo(String nearMe) {
        char atWay = ' ';
        int random = 0;
        do {
            random = new java.util.Random().nextInt(4);
            switch (random) {
                case 0:
                    atWay = nearMe.charAt(1);
                    break;
                case 1:
                    atWay = nearMe.charAt(5);
                    break;
                case 2:
                    atWay = nearMe.charAt(7);
                    break;
                case 3:
                    atWay = nearMe.charAt(3);
                    break;
                default:
                    atWay = nearMe.charAt(5);
                    break;
            }

        } while (atWay != ' ' && atWay != '$');

        switch (new java.util.Random().nextInt(4)) {
            case 0:
                return "up";
            case 1:
                return "down";
            case 2:
                return "left";
            case 3:
                return "right";
            default:
                return "down";
        }
    }

}
