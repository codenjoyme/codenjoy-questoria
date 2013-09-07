package apofig.javaquest;

import apofig.javaquest.console.Console;
import apofig.javaquest.console.ConsoleImpl;
import apofig.javaquest.console.Runner;
import apofig.javaquest.map.*;
import apofig.javaquest.map.object.monster.MonsterFactoryImpl;
import apofig.javaquest.map.object.monster.MonsterLoader;
import apofig.javaquest.map.object.monster.MonsterPool;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 7:10 PM
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Settings settings = new Settings() {
            @Override
            public int viewSize() {
                return 41;
            }

            @Override
            public MapLoader mapLoader() {
                return new LoadMapFromFile("map.txt");
            }

            @Override
            public MonsterPool monsters() {
                return new MonsterFactoryImpl(new MonsterLoader());
            }
        };
        JavaQuestSinglePlayer game = new JavaQuestSinglePlayer(new JavaQuest(settings), "You");
        Console console = new ConsoleImpl();
        new Runner(game, console).playGame();
    }
}
