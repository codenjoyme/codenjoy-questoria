package apofig.javaquest;

import apofig.javaquest.console.Console;
import apofig.javaquest.console.ConsoleImpl;
import apofig.javaquest.console.Runner;
import apofig.javaquest.map.JavaQuest;
import apofig.javaquest.map.Settings;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 7:10 PM
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Settings settings = new Settings() {
            @Override
            public int getWorldSize() {
                return 100;
            }

            @Override
            public int getViewAreaSize() {
                return 33;
            }
        };
        JavaQuest game = new JavaQuest(settings);
        Console console = new ConsoleImpl();
        new Runner(game, console).playGame();
    }
}
