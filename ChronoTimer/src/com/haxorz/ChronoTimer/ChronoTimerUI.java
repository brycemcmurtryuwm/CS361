package com.haxorz.ChronoTimer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChronoTimerUI extends JFrame{
	JButton[] _numPad = new JButton[12];

	JTextPane _screen;
	public ChronoTimerUI(){
		this.setSize(1000,700);
		this.setResizable(false);
		this.setTitle("ChronoTimer 1009");
		createComponents();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void createComponents(){
		JPanel front = new JPanel(new GridLayout(2,3));

		Font font = new Font("SansSerif", Font.PLAIN, 20);
		Font font2 = new Font("SansSerif", Font.PLAIN, 32);
		setFont(font);


		//powerButton
		JPanel powerPanel = new JPanel();
		JButton powerButton = new JButton("Power");
		powerButton.setFont(font);
		powerPanel.add(powerButton);


		front.add(powerPanel);

		//sensorGrid
		JPanel channelsPanel = new JPanel();

		JPanel channelsLabels = new JPanel();
		channelsLabels.setSize(100,350);
		JLabel startLabel = new JLabel("Start");
		startLabel.setFont(font);
		JLabel enableLabel1 = new JLabel("Enable");
		enableLabel1.setFont(font);
		JLabel finishLabel = new JLabel("Finish");
		finishLabel.setFont(font);
		JLabel enableLabel2 = new JLabel("Enable");
		enableLabel2.setFont(font);
		channelsLabels.add(startLabel);
		channelsLabels.add(enableLabel1);
		channelsLabels.add(finishLabel);
		channelsLabels.add(enableLabel2);
		channelsPanel.add(channelsLabels);


		GridLayout sensorLayout = new GridLayout(6,4);
		sensorLayout.setHgap(7);
		JPanel sensorGrid = new JPanel(sensorLayout);
		Border sensorsBoarder = new BevelBorder(1);
		//sensorGrid.setBorder(new EmptyBorder(50,50,50,50));
		JButton[] startButtons = new JButton[8];
		JCheckBox[] enableBoxes = new JCheckBox[8];

		JLabel one = new JLabel("1");
		one.setFont(font);
		sensorGrid.add(one);
		JLabel three = new JLabel("3");
		three.setFont(font);
		sensorGrid.add(three);
		JLabel five = new JLabel("5");
		five.setFont(font);
		sensorGrid.add(five);
		JLabel seven = new JLabel("7");
		seven.setFont(font);
		sensorGrid.add(seven);
		for(int i = 0; i < 4; i++) {
			startButtons[i] = new JButton();
			startButtons[i].setOpaque(true);
			startButtons[i].setBackground(Color.RED);
			sensorGrid.add(startButtons[i]);
		}
		for(int i = 0; i < 4; i++) {
			enableBoxes[i] = new JCheckBox();
			sensorGrid.add(enableBoxes[i]);
		}

		JLabel two = new JLabel("2");
		two.setFont(font);
		sensorGrid.add(two);
		JLabel four = new JLabel("4");
		four.setFont(font);
		sensorGrid.add(four);
		JLabel six = new JLabel("6");
		six.setFont(font);
		sensorGrid.add(six);
		JLabel eight = new JLabel("8");
		eight.setFont(font);
		sensorGrid.add(eight);
		for(int i = 4; i < 8; i++) {
			startButtons[i] = new JButton();
			startButtons[i].setOpaque(true);
			startButtons[i].setBackground(Color.RED);
			sensorGrid.add(startButtons[i]);
		}
		for(int i = 4; i < 8; i++) {
			enableBoxes[i] = new JCheckBox();
			sensorGrid.add(enableBoxes[i]);
		}
		channelsPanel.add(sensorGrid);
		front.add(channelsPanel);

		//printer
		JPanel printerPanel = new JPanel();
		JButton printerPower = new JButton("Printer Pwr");
		printerPanel.add(printerPower);
		JTextPane printer = new JTextPane();
		printer.setPreferredSize(new Dimension(250,280));
		printer.setMaximumSize(new Dimension(250,280));
		printerPanel.add(printer);
		front.add(printerPanel);

		//Function, arrows, and Swap
		JPanel randomButtons = new JPanel();
		randomButtons.setBorder(new EmptyBorder(0,50,0,50));
		JButton functionButton = new JButton("Function");
		functionButton.setFont(font);
		functionButton.setAlignmentX(2);
		randomButtons.add(functionButton);
		JPanel arrows = new JPanel();
		JButton leftButton = new JButton("<");
		leftButton.setFont(font);
		arrows.add(leftButton);
		JButton rightButton = new JButton(">");
		rightButton.setFont(font);
		arrows.add(rightButton);
		JButton downButton = new JButton("v");
		downButton.setFont(font);
		arrows.add(downButton);
		JButton upButton = new JButton("^");
		upButton.setFont(font);
		arrows.add(upButton);
		randomButtons.add(arrows);

		randomButtons.add(new JLabel("                                                    "));	//filler
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));


		JButton swapButton= new JButton("Swap");
		swapButton.setFont(font);
		randomButtons.add(swapButton);
		front.add(randomButtons);

		//screenPanel
		JPanel screenPanel = new JPanel();
		//screenPanel.setPreferredSize(new Dimension(120,200));
		_screen = new JTextPane();
		_screen.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		//_screen.setBorder(new EmptyBorder(10,10,5,10));
		_screen.setPreferredSize(new Dimension(250,280));
		_screen.setMaximumSize(new Dimension(250,280));

		screenPanel.add(_screen);
		JLabel screenInfo = new JLabel("Queue/Running/Final Time");
		screenInfo.setFont(font);
		screenPanel.add(screenInfo, BorderLayout.SOUTH);
		front.add(screenPanel, BorderLayout.CENTER);

		//numpanel
		GridLayout numLayout = new GridLayout(4,3);
		JPanel numPanel = new JPanel(numLayout);
		numPanel.setSize(180,240);

		for(int i = 1; i < 10; i++) {
			_numPad[i] = new JButton("" + i);
			_numPad[i].setFont(font2);
			_numPad[i].addActionListener(new numPadListener());
			numPanel.add(_numPad[i]);

		}
		_numPad[10] = new JButton("*");
		_numPad[10].setFont(font2);
		_numPad[10].addActionListener(new numPadListener());
		numPanel.add(_numPad[10]);
		_numPad[0] = new JButton("0");
		_numPad[0].setFont(font2);
		_numPad[0].addActionListener(new numPadListener());
		numPanel.add(_numPad[0]);
		_numPad[11] = new JButton("#");
		_numPad[11].setFont(font2);
		_numPad[11].addActionListener(new numPadListener());
		numPanel.add(_numPad[11]);
		JPanel numPanelCover = new JPanel(new FlowLayout());
		numPanelCover.add(numPanel);
		front.add(numPanelCover,BorderLayout.EAST);





		this.add(front, BorderLayout.CENTER);


	}
	public class numPadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	public static void main(String[] args){
		new ChronoTimerUI();
	}
}


