package apofig.javaquest.console;

import apofig.javaquest.field.JavaQuestSinglePlayer;
import apofig.javaquest.field.Joystick;

public class Runner {
	
	private JavaQuestSinglePlayer game;
	private Console console;
		
	public Runner(JavaQuestSinglePlayer game, Console console) {
		this.game = game;
		this.console = console;
	}

	public void playGame() {
		Joystick player = game.getJoystick();

        int ch = 0;
		do {		
			printBoard();
			
			String line = console.read();
            if (line.length() == 1) {
				ch = line.charAt(0);
            }

            if (line.length() <= 1) {
                if (ch == 'x' || ch == 'ч') {
                    player.moveDown();
                } else if (ch == 'a' || ch == 'ф') {
                    player.moveLeft();
                } else if (ch == 'd' || ch == 'в') {
                    player.moveRight();
                } else if (ch == 's' || ch == 'ы') {
                    player.moveUp();
                } else if (ch == 'q' || ch == 'й') {
                    break;
                } else {
                    player.attack(String.valueOf(ch));
                }
            } else {
                player.attack(line);
            }

            game.tick();

		} while (true);
		
		console.print("Game over!");
	}
	
	private void printBoard() {
        console.print(game.toString() + "\n" + game.getMessage());
	}

}
