package main;

import java.util.ArrayList;

import ui.Options;
import ui.UI;

public class Start {

	public static boolean active;
	
	public static GameBoard game;
	private static Thread graphics;
	
	public static void main(String[] args) {
		ArrayList<Cell> cells = ReadCell.readCells();
		
		game = new GameBoard(cells);
		UI ui = new UI();
		
		graphics = new Thread(ui);
		graphics.start();
		
		active = true;
		long prevTime = System.currentTimeMillis();
		while(active) {
			long time = System.currentTimeMillis();
			if(Options.getActive() && time >= prevTime + Options.getSpeed()) {
				game.updateCells();
				prevTime = time;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Ends simulation and closes program.
	 */
	public static void close() {
		active = false;
		System.exit(0);
	}

}
