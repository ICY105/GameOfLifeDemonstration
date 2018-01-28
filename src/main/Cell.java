package main;

import java.awt.Color;

public class Cell {
	
	public Cell(String name, byte id, Color color, int value, int priority, boolean spontaneous, int aliveMin, int aliveMax, int createMin, int createMax) {
		this.name = name;
		this.id = id;
		this.color = color;
		this.priority = priority;
		this.spontaneous = spontaneous;
		
		this.value = value;
		this.aliveMin = aliveMin;
		this.aliveMax = aliveMax;
		this.createMin = createMin;
		this.createMax = createMax;
	}
	
	public final String name;         //Name, used for display purposes
	public final byte id;             //Unique int ID, auto assigned
	public final Color color;         //Display color
	
	public final int value;           //Value counted by nearby cells for death/birth/alive
	public final int priority;        //The cell with the higher priority will override other cells for birth/death
	public final boolean spontaneous; //Determines if a nearby cell of the same type is required to be born.
	public final int aliveMin;        //Min value needed to remain alive
	public final int aliveMax;        //Max value allowed to remain alive
	public final int createMin;       //Min value needed to create a cell
	public final int createMax;       //Max value allows to stay alive
	
}
