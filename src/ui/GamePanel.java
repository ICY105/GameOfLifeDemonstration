package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.GameBoard;
import main.Start;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	private static final int SIZE = 600;
	
	private int cellSize;
	
	private int offsetX;
	private int offsetY;
	
	private int[] mouseOffset;
	
	public GamePanel() {		
		this.setPreferredSize(new Dimension(SIZE,SIZE));
		this.setBackground(Color.black);
		this.setBorder(BorderFactory.createBevelBorder(1,Color.BLACK, Color.GRAY));
		
		cellSize = 20;
		offsetX = 1;
		offsetY = 1;
		
		mouseOffset = new int[5];
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == 1) {
					int x = (arg0.getX() - offsetX)/cellSize;
					int y = (arg0.getY() - offsetY)/cellSize;
					
					if(x >= 0 && x < GameBoard.GAMESIZE && y >= 0 && y < GameBoard.GAMESIZE)
						Start.game.updateCell(x,y,Options.getSelected());
				}
				repaint();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {
				mouseOffset[0] = arg0.getButton();
				mouseOffset[1] = arg0.getX();
				mouseOffset[2] = arg0.getY();
				mouseOffset[3] = offsetX;
				mouseOffset[4] = offsetY;
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(mouseOffset[0] == 3) {
					offsetX = (mouseOffset[1] - arg0.getX()) * -1 + mouseOffset[3];
					offsetY = (mouseOffset[2] - arg0.getY()) * -1 + mouseOffset[4];
				}
				if(mouseOffset[0] == 1) {
					int x = (arg0.getX() - offsetX)/cellSize;
					int y = (arg0.getY() - offsetY)/cellSize;
					
					if(x >= 0 && x < GameBoard.GAMESIZE && y >= 0 && y < GameBoard.GAMESIZE)
						Start.game.updateCell(x,y,Options.getSelected());
				}
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {}
		});
		
		this.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				if(arg0.getWheelRotation() > 0) {
					cellSize++;
					offsetX -= 20;
					offsetY -= 20;
				} else if(arg0.getWheelRotation() < 0 && cellSize > 2) {
					cellSize--;
					offsetX += 20;
					offsetY += 20;
				}
				repaint();
			}
		});
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		for(int i = 0; i < GameBoard.GAMESIZE; i++) {
			for(int j = 0; j < GameBoard.GAMESIZE; j++) {
				g2d.setColor(Start.game.getCellType(i, j).color);
				g2d.fillRect(cellSize * i + offsetX, cellSize * j + offsetY, cellSize, cellSize);
				
				g2d.setColor(Color.BLACK);
				if(cellSize > 2)
					g2d.drawRect(cellSize * i + offsetX, cellSize * j + offsetY, cellSize, cellSize);
    	   }
		}		
    }
	
}
