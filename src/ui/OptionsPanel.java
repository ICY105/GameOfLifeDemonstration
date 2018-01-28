package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import main.Cell;
import main.GameBoard;
import main.Start;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	
	public OptionsPanel() {
		this.setPreferredSize(new Dimension(280,600));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createBevelBorder(1,Color.BLACK, Color.GRAY));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new JLabel(" "));
		state = new JButton();
		state.setText("          off          ");
		state.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(state.getText().equals("          off          "))
					state.setText("          on          ");
				else
					state.setText("          off          ");
			}
		});
		this.add(state);
		
		next = new JButton("Next Iteration");
		next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!Options.active)
					Start.game.updateCells();
			}
		});
		this.add(next);
		
		this.add(new JLabel(" "));
		
		this.add(new JLabel("Speed:"));
		speed = new JSlider(JSlider.HORIZONTAL,10,2000,10);	
		speed.setMajorTickSpacing(250);
		speed.setMinorTickSpacing(25);
		speed.setPaintTicks(true);
		this.add(speed);
		
		this.add(new JLabel(" "));
		
		randomAll = new JButton("      Randomize All      ");
		randomAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < GameBoard.GAMESIZE; i++) {
					for(int j = 0; j < GameBoard.GAMESIZE; j++) {
						Random random = new Random();
						Start.game.updateCell(i, j, 
								(byte) Start.game.getCellTypes().get(
									random.nextInt(Start.game.getCellTypes().size())
								).id);
					}
				}
			}
		});
		this.add(randomAll);
		
		randomSelected = new JButton("Randomize Selected");
		randomSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < GameBoard.GAMESIZE; i++) {
					for(int j = 0; j < GameBoard.GAMESIZE; j++) {
						if(Math.random() > 0.5)
							Start.game.updateCell(i, j, Options.getSelected());
						else
							Start.game.updateCell(i, j, (byte)0);
					}
				}
			}
		});
		this.add(randomSelected);
		
		this.add(new JLabel(" "));
		
		fill = new JButton("                 Fill                 ");
		fill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < GameBoard.GAMESIZE; i++) {
					for(int j = 0; j < GameBoard.GAMESIZE; j++) {
						Start.game.updateCell(i, j, Options.getSelected());
					}
				}
			}
		});
		this.add(fill);
		
		this.add(new JLabel(" "));
		
		this.add(new JLabel("Cell Types:"));
		
		cells = new JButton[Start.game.getCellTypes().size()];
		for(int i = 0; i < Start.game.getCellTypes().size(); i++) {
			cells[i] = prepairCellButton(Start.game.getCellTypes().get(i));
			this.add(cells[i]);
		}
	}
	
	JSlider speed;
	JButton state;
	JButton next;
	JButton randomAll;
	JButton randomSelected;
	JButton clear;
	JButton fill;
	JButton[] cells;
	
	private JButton prepairCellButton(Cell cell) {
		JButton out = new JButton(cell.name.replace("_", "_"));
		out.setBackground(cell.color);
		out.setForeground(Color.WHITE);
		
		out.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Options.selected = cell.id;
			}
		});
		return out;
	}
}
