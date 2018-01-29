package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadCell {
	
	/**
	 * Loads cell information from external text files.
	 * @return List of loaded cells.
	 */
	public static ArrayList<Cell> readCells() {
		ArrayList<Cell> out = new ArrayList<Cell>();
		
		File cellFile = new File("game_of_life");
		if(!cellFile.exists())
			generateDefault(cellFile);		
		File[] cells = cellFile.listFiles();
		
		out.add(new Cell("Dead", (byte)0, Color.DARK_GRAY, 0, 0, false, 0, 0, 0, 0));
		
		for(File e:cells) {
			if(e.getName().length() > 4 && e.getName().substring(e.getName().length()-4).equals(".txt"))
				out.add(processFile(e));
		}
		
		return out;
	}
	
	private static int cellID = 1;
	/**
	 * Reads info from text file to create Cell object.
	 * @param file to read
	 * @return Generated Cell
	 */
	private static Cell processFile(File file) {
		int id = cellID;
		cellID += 2;
		Color color = Color.BLACK;
		int priority = 0;
		boolean spontaneous = false;
		
		int value = 0;
		int aliveMin = 0;
		int aliveMax = 0;
		int createMin = 0;
		int createMax = 0;
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String in = br.readLine();
			while(in != null) {				
				if(in.length() >= 11 && in.substring(0, 6).equals("color=")) {
					String[] colorText = in.substring(6).split(",");
					int r = Integer.parseInt(colorText[0]);
					int g = Integer.parseInt(colorText[1]);
					int b = Integer.parseInt(colorText[2]);
					
					float[] hsb = Color.RGBtoHSB(r, g, b, null);
					color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
					
				} else if(in.length() > 9 && in.substring(0, 9).equals("priority=")) {
					priority = Integer.parseInt(in.substring(9));
					
				} else if(in.length() > 6 && in.substring(0, 6).equals("value=")) {
					value = Integer.parseInt(in.substring(6));
					
				} else if(in.length() > 9 && in.substring(0, 9).equals("aliveMin=")) {
					aliveMin = Integer.parseInt(in.substring(9));
					
				} else if(in.length() > 9 && in.substring(0, 9).equals("aliveMax=")) {
					aliveMax = Integer.parseInt(in.substring(9));
					
				} else if(in.length() > 10 && in.substring(0, 10).equals("createMin=")) {
					createMin = Integer.parseInt(in.substring(10));
					
				} else if(in.length() > 10 && in.substring(0, 10).equals("createMax=")) {
					createMax = Integer.parseInt(in.substring(10));
				} else if(in.length() > 10 && in.substring(0, 12).equals("spontaneous=")) {
					spontaneous = in.substring(12).equals("true");
				}
				in = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException | NumberFormatException e) {
			System.out.println("An error occured loading file " + file.getName() + ".");
		}
		return new Cell(file.getName().substring(0,file.getName().length()-4), (byte)id, color, value, priority, spontaneous, aliveMin, aliveMax, createMin, createMax);
	}
	
	/**
	 * Generates default folder & cell at given location.
	 */
	private static void generateDefault(File file) {
		file.mkdir();
		File cell = new File("game_of_life/Basic_Cell.txt");
		try {
			cell.createNewFile();
			FileWriter out = new FileWriter(cell);
			
			out.write("color=0,0,255\n");
			out.write("priority=1\n");
			out.write("value=1\n");
			out.write("spontaneous=false\n");
			out.write("aliveMin=2\n");
			out.write("aliveMax=3\n");
			out.write("createMin=3\n");
			out.write("createMax=3");
			
			out.close();
		} catch (IOException e) {}
	}
	
}
