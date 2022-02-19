package apofig.javaquest;

import apofig.javaquest.console.Console;
import apofig.javaquest.console.ConsoleImpl;
import apofig.javaquest.console.Runner;
import apofig.javaquest.field.*;
import apofig.javaquest.field.object.monster.MonsterFactory;
import apofig.javaquest.field.object.monster.MonsterPoolImpl;
import apofig.javaquest.field.object.monster.MonsterLoader;
import apofig.javaquest.field.object.monster.MonsterPool;

public class Main {

    public static void main(String[] args) throws Exception {
        Settings settings = new Settings() {
            @Override
            public int viewSize() {
                return 41;
            }

            @Override
            public FieldLoader fieldLoader() {
                return new LoadFieldFromFile("field.txt");
            }

            @Override
            public MonsterFactory monsters() {
                return new MonsterFactory() {
                    @Override
                    public MonsterPool newMonsters() {
                        return new MonsterPoolImpl(new MonsterLoader());
                    }
                };
            }
        };
        JavaQuestSinglePlayer game = new JavaQuestSinglePlayer(new JavaQuest(settings), "You");
        Console console = new ConsoleImpl();
        new Runner(game, console).playGame();
    }
}
