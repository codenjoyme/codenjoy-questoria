package apofig.javaquest.console;

import apofig.javaquest.map.JavaQuest;
import apofig.javaquest.map.Joystick;

public class Runner {
	
	private JavaQuest game;
	private Console console;
		
	public Runner(JavaQuest game, Console console) {
		this.game = game;
		this.console = console;
	}

	public void playGame() {
		Joystick player = game.getPlayer();

        int ch = 0;
		do {		
			printBoard();
			
			String line = console.read();
            if (line.length() != 0) {
				ch = line.charAt(0);
            }
				
            if (ch == 115 || ch == 1099) {
                player.moveDown();
            } else if (ch == 97 || ch == 1092) {
                player.moveLeft();
            } else if (ch == 100 || ch == 1074) {
                player.moveRight();
            } else if (ch == 119 || ch == 1094) {
                player.moveUp();
            } else if (ch == 'q') {
                break;
            }
//            game.tact();
		} while (true);
		
		console.print("Game over!");
	}
	
	private void printBoard() {
		console.print(game.getTerritoryMap().getViewArea());
	}

}
