package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import main.GameBoard;
import main.Start;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	
	public OptionsPanel() {
		this.setPreferredSize(new Dimension(280,600));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createBevelBorder(1,Color.BLACK, Color.GRAY));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new JLabel("Simulation Toggle:"));
		state = new JButton();
		state.setText("off");
		state.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(state.getText().equals("off"))
					state.setText("on");
				else
					state.setText("off");
			}
		});
		this.add(state);
		
		this.add(new JLabel("Speed:"));
		speed = new JSlider(JSlider.HORIZONTAL,10,2000,10);	
		speed.setMajorTickSpacing(250);
		speed.setMinorTickSpacing(25);
		speed.setPaintTicks(true);
		this.add(speed);
		
		random = new JButton("Randomize");
		random.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < GameBoard.GAMESIZE; i++) {
					for(int j = 0; j < GameBoard.GAMESIZE; j++) {
						if(Math.random() > 0.5)
							Start.game.updateCell(i, j, true);
						else
							Start.game.updateCell(i, j, false);
					}
				}
			}
		});
		this.add(random);
		
		this.add(random);
		clear = new JButton("Clear           ");
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < GameBoard.GAMESIZE; i++) {
					for(int j = 0; j < GameBoard.GAMESIZE; j++) {
						Start.game.updateCell(i, j, false);
					}
				}
			}
		});
		this.add(clear);
		
		fill = new JButton("Fill                ");
		fill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < GameBoard.GAMESIZE; i++) {
					for(int j = 0; j < GameBoard.GAMESIZE; j++) {
						Start.game.updateCell(i, j, true);
					}
				}
			}
		});
		this.add(fill);
	}
	
	JSlider speed;
	JButton state;
	JButton random;
	JButton clear;
	JButton fill;
}
