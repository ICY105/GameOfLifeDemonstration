package main;

import java.util.ArrayList;

public class GameBoard {
	
	public static final int GAMESIZE = 256;
	
	public GameBoard(ArrayList<Cell> cells) {
		game = new byte[GAMESIZE][GAMESIZE];
		this.cells = cells;
	}
	
	private byte[][] game;
	private ArrayList<Cell> cells;
	
	public byte[][] getMap() {
		return game;
	}
	
	public ArrayList<Cell> getCellTypes() {
		return cells;
	}
	
	/**
	 * Updates a single position on the game board.
	 * @param x cord
	 * @param y cord
	 * @param state to put cell in.
	 */
	public void updateCell(int x, int y, byte state) {
		game[x][y] = state;
	}
	
	/**
	 * 
	 * @param x cord
	 * @param y cord
	 * @return Cell object for given cord.
	 */
	public Cell getCellType(int x, int y) {
		int cell = game[x][y];
		if(cell != 0 && cell%2 == 0)
			cell -= 1;
		
		Cell out = cells.get(0);
		for(int e = 1; e < cells.size(); e++) {
			if(cells.get(e).id == Math.abs(cell)) {
				out = cells.get(e);
			}
		}
		return out;
	}
	
	/**
	 * Runs update cycle.
	 */
	public void updateCells() {
		for(int i = 0; i < GAMESIZE; i++) {
			for(int j = 0; j < GAMESIZE; j++) {
				int nearby = 0;
				
				if(i > 0)
					nearby += getCellValue(i-1,j);
				if(i+1 < GAMESIZE)
					nearby += getCellValue(i+1,j);
				if(j > 0)
					nearby += getCellValue(i,j-1);
				if(j+1 < GAMESIZE)
					nearby += getCellValue(i,j+1);
				
				if(i+1 < GAMESIZE && j+1 < GAMESIZE)
					nearby += getCellValue(i+1,j+1);
				if(i+1 < GAMESIZE && j > 0)
					nearby += getCellValue(i+1,j-1);
				if(i > 0 && j+1 < GAMESIZE)
					nearby += getCellValue(i-1,j+1);
				if(i > 0 && j > 0)
					nearby += getCellValue(i-1,j-1);
				
				if(game[i][j] == 0 && nearby != 0) {
					Cell cell = cells.get(0);
					for(int e = 1; e < cells.size(); e++) {
						if(nearby >= cells.get(e).createMin && nearby <= cells.get(e).createMax && cells.get(e).priority > cell.priority) {
							if((!cells.get(e).spontaneous && nearbyType(i,j,cells.get(e).id)) || cells.get(e).spontaneous)
								cell = cells.get(e);
						}
					}
					game[i][j] = (byte) (-1 * cell.id);
				} else {
					Cell cell = getCellType(i,j);
					if(nearby < cell.aliveMin || nearby > cell.aliveMax)
						game[i][j] += 1;
				}
			}
		}
		
		for(int i = 0; i < GAMESIZE; i++) {
			for(int j = 0; j < GAMESIZE; j++) {
				if(game[i][j]%2 == 0)
					game[i][j] = 0;
				if(game[i][j] < 0)
					game[i][j] = (byte) (-1 * game[i][j]);
			}
		}
	}
	
	/**
	 * @param x cord
	 * @param y cord
	 * @param id of cell to check
	 * @return if an adjacent cell is of the same type as id
	 */
	private boolean nearbyType(int x, int y, int id) {
		boolean out = false;
		
		if(x > 0 && getCellType(x-1,y).id == id)
			out = true;
		if(x+1 < GAMESIZE && getCellType(x+1,y).id == id)
			out = true;
		if(y > 0 && getCellType(x,y-1).id == id)
			out = true;
		if(y+1 < GAMESIZE && getCellType(x,y+1).id == id)
			out = true;
		
		if(x+1 < GAMESIZE && y+1 < GAMESIZE && getCellType(x+1,y+1).id == id)
			out = true;
		if(x+1 < GAMESIZE && y > 0 && getCellType(x+1,y-1).id == id)
			out = true;
		if(x > 0 && y+1 < GAMESIZE && getCellType(x-1,y+1).id == id)
			out = true;
		if(x > 0 && y > 0 && getCellType(x-1,y-1).id == id)
			out = true;
		
		return out;
	}
	
	/**
	 * @param x cord
	 * @param y cord
	 * @return Value of cell at given position
	 */
	private int getCellValue(int x, int y) {
		int out = 0;
		if(game[x][y] > 0)
			out = getCellType(x,y).value;
		return out;
	}
	
}
