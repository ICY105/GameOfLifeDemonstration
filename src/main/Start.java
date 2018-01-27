package main;

import ui.UI;

public class Start {

	private static boolean active;
	
	public static GameBoard game;
	
	public static void main(String[] args) {
		game = new GameBoard();
		UI ui = new UI();
		
		active = true;
		while(active) {
			if(ui.options.getActive())
				game.updateCells();
			ui.updateGameUI();
			
			try {
				Thread.sleep(ui.options.getSpeed());
			} catch (InterruptedException e) {}
		}
	}
	
	public static void close() {
		active = false;
		System.exit(0);
	}

}
