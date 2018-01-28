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
	
	public void updateCell(int x, int y, byte state) {
		game[x][y] = state;
	}
	
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
	
	private boolean nearbyType(int i, int j,int id) {
		boolean out = false;
		
		if(i > 0 && getCellType(i-1,j).id == id)
			out = true;
		if(i+1 < GAMESIZE && getCellType(i+1,j).id == id)
			out = true;
		if(j > 0 && getCellType(i,j-1).id == id)
			out = true;
		if(j+1 < GAMESIZE && getCellType(i,j+1).id == id)
			out = true;
		
		if(i+1 < GAMESIZE && j+1 < GAMESIZE && getCellType(i+1,j+1).id == id)
			out = true;
		if(i+1 < GAMESIZE && j > 0 && getCellType(i+1,j-1).id == id)
			out = true;
		if(i > 0 && j+1 < GAMESIZE && getCellType(i-1,j+1).id == id)
			out = true;
		if(i > 0 && j > 0 && getCellType(i-1,j-1).id == id)
			out = true;
		
		return out;
	}

	private int getCellValue(int x, int y) {
		int out = 0;
		if(game[x][y] > 0)
			out = getCellType(x,y).value;
		return out;
	}
	
}
