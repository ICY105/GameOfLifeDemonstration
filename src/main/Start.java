package main;

import java.util.ArrayList;

import ui.Options;
import ui.UI;

public class Start {

	private static boolean active;
	
	public static GameBoard game;
	
	public static void main(String[] args) {
		ArrayList<Cell> cells = ReadCell.readCells();
		
		game = new GameBoard(cells);
		UI ui = new UI();
		
		active = true;
		while(active) {
			if(Options.getActive())
				game.updateCells();
			ui.updateGameUI();
			
			try {
				Thread.sleep(Options.getSpeed());
			} catch (InterruptedException e) {}
		}
	}
	
	public static void close() {
		active = false;
		System.exit(0);
	}

}
