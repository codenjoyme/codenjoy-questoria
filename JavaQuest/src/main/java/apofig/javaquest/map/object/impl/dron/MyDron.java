package apofig.javaquest.map.object.dron;

import java.util.Random;

/**
 * User: sanja
 * Date: 01.09.13
 * Time: 2:35
 */
public class MyDron {

    public String whereToGo(String nearMe) {
//        if (nearMe.charAt(1) == '$' || nearMe.charAt(2) == '$' || nearMe.charAt(3) == '$') {
//            return "up";
//        } else if (nearMe.charAt(4) == '$') {
//            return "right";
//        } else if (nearMe.charAt(8) == '$') {
//            return "left";
//        } else if (nearMe.charAt(7) == '$' || nearMe.charAt(6) == '$' || nearMe.charAt(5) == '$') {
//            return "down";
//        }

        int random = new java.util.Random().nextInt(4);
        switch (random) {
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
