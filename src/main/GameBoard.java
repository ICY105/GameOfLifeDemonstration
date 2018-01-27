package main;

public class GameBoard {
	
	public static final int GAMESIZE = 256;
	
	public GameBoard() {
		game = new byte[GAMESIZE][GAMESIZE];
	}
	
	private byte[][] game;
	
	public byte[][] getMap() {
		return game;
	}
	
	public void updateCell(int x, int y, boolean state) {
		if(state) {
			game[x][y] = 1;
		} else {
			game[x][y] = 0;
		}
	}
	
	public boolean isCellAlive(int x, int y) {
		return getState(game[x][y]) == 1;
	}
	
	public void updateCells() {
		for(int i = 0; i < GAMESIZE; i++) {
			for(int j = 0; j < GAMESIZE; j++) {
				int nearby = 0;
				
				if(i > 0)
					nearby += getState(game[i-1][j]);
				if(i+1 < GAMESIZE)
					nearby += getState(game[i+1][j]);
				if(j > 0)
					nearby += getState(game[i][j-1]);
				if(j+1 < GAMESIZE)
					nearby += getState(game[i][j+1]);
				
				if(i+1 < GAMESIZE && j+1 < GAMESIZE)
					nearby += getState(game[i+1][j+1]);
				if(i+1 < GAMESIZE && j > 0)
					nearby += getState(game[i+1][j-1]);
				if(i > 0 && j+1 < GAMESIZE)
					nearby += getState(game[i-1][j+1]);
				if(i > 0 && j > 0)
					nearby += getState(game[i-1][j-1]);
				
				if(getState(game[i][j]) == 1 && !(nearby == 2 || nearby == 3)) {
					game[i][j] = 2;
				}
				if(getState(game[i][j]) == 0 && nearby == 3) {
					game[i][j] = 3;
				}
			}
		}
		
		for(int i = 0; i < GAMESIZE; i++) {
			for(int j = 0; j < GAMESIZE; j++) {
				if(game[i][j] == 2)
					game[i][j] = 0;
				if(game[i][j] == 3)
					game[i][j] = 1;
			}
		}
	}
	
	private int getState(int cell) {
		int out = 0;
		if(cell == 1 || cell == 2)
			out = 1;
		return out;
	}
	
	
	
}
