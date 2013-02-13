package apofig.javaquest;

import apofig.javaquest.console.Console;
import apofig.javaquest.console.ConsoleImpl;
import apofig.javaquest.console.Runner;
import apofig.javaquest.map.*;
import apofig.javaquest.map.object.monster.MonsterPool;
import apofig.javaquest.map.object.monster.MonsterFactoryImpl;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 7:10 PM
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Settings settings = new Settings() {
            @Override
            public int getViewAreaSize() {
                return 41;
            }

            @Override
            public MapLoader getMapLoader() {
                return new LoadMapFromFile("map.txt");
            }

            @Override
            public MonsterPool getMonsters() {
                return new MonsterFactoryImpl();
            }
        };
        JavaQuest game = new JavaQuest(settings);
        Console console = new ConsoleImpl();
        new Runner(game, console).playGame();
    }
}
