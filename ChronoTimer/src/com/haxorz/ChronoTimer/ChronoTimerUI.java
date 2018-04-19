package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Commands.GenericCmd;
import com.haxorz.ChronoTimer.Commands.ToggleCmd;
import com.haxorz.ChronoTimer.Commands.TriggerCmd;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

public class ChronoTimerUI extends JFrame{
	ChronoTimer _timer;
	JButton[] _numPad = new JButton[12];
	JTextPane _screen;
	public ChronoTimerUI(){
		this.setSize(1000,850);
		this.setResizable(false);
		this.setTitle("ChronoTimer 1009");
		createComponents();
		_timer = new ChronoTimer(new JPanelPrintStream(_screen));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void createComponents(){
		JPanel front = new JPanel(new GridLayout(2,3));

		Font font = new Font("SansSerif", Font.PLAIN, 20);
		Font font2 = new Font("SansSerif", Font.PLAIN, 40);
		Font font3 = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
		setFont(font);


		//powerButton
		JPanel powerPanel = new JPanel();
		JButton powerButton = new JButton("Power");
		powerButton.setFont(font);
		powerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_timer.executeCmd(new GenericCmd(CmdType.POWER, LocalTime.now()));
			}
		});
		powerPanel.add(powerButton);
		front.add(powerPanel);

		//sensorGrid
		JPanel channelsPanel = new JPanel();
		JLabel startLabel = new JLabel("Start");
		startLabel.setFont(font3);
		JLabel enableLabel1 = new JLabel("Enable");
		enableLabel1.setFont(font3);
		JLabel finishLabel = new JLabel("Finish");
		finishLabel.setFont(font3);
		JLabel enableLabel2 = new JLabel("Enable");
		enableLabel2.setFont(font3);



		GridLayout sensorLayout = new GridLayout(6,5);
		sensorLayout.setHgap(7);
		JPanel sensorGrid = new JPanel(sensorLayout);
		sensorGrid.setBorder(new EmptyBorder(100,0,50,0));
		JButton[] startButtons = new JButton[8];
		JCheckBox[] enableBoxes = new JCheckBox[8];


		sensorGrid.add(new JPanel());
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
		sensorGrid.add(startLabel);
		for(int i = 0; i <= 7; i+=2) {
			startButtons[i] = new JButton();
			startButtons[i].setOpaque(true);
			startButtons[i].setBackground(Color.RED);
			int finalI = i;
			startButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					_timer.executeCmd(new TriggerCmd(LocalTime.now(), finalI + 1));
				}
			});
			sensorGrid.add(startButtons[i]);
		}
		sensorGrid.add(enableLabel1);
		for(int i = 0; i <= 7; i+=2) {
			enableBoxes[i] = new JCheckBox();
			int finalI = i;
			enableBoxes[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					_timer.executeCmd(new ToggleCmd(LocalTime.now(), finalI + 1));
				}
			});
			sensorGrid.add(enableBoxes[i]);
		}
		sensorGrid.add(new JPanel());
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
		sensorGrid.add(finishLabel);
		for(int i = 1; i <= 7; i+=2) {
			startButtons[i] = new JButton();
			startButtons[i].setOpaque(true);
			startButtons[i].setBackground(Color.RED);
			int finalI = i;
			startButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					_timer.executeCmd(new ToggleCmd(LocalTime.now(), finalI + 1));
				}
			});
			sensorGrid.add(startButtons[i]);
		}
		sensorGrid.add(enableLabel2);
		for(int i = 1; i <= 7; i+=2) {
			enableBoxes[i] = new JCheckBox();
			sensorGrid.add(enableBoxes[i]);
			int finalI = i;
			enableBoxes[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					_timer.executeCmd(new TriggerCmd(LocalTime.now(), finalI + 1));
				}
			});
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
		JButton leftButton = new JButton(""+(char)9668);
		leftButton.setFont(font);
		arrows.add(leftButton);
		JButton rightButton = new JButton(""+(char)9658);
		rightButton.setFont(font);
		arrows.add(rightButton);
		JButton downButton = new JButton(""+(char)9660);
		downButton.setFont(font);
		arrows.add(downButton);
		JButton upButton = new JButton(""+(char)9650);
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
		_screen = new JTextPane();
		_screen.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
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

		JPanel back = new JPanel();
		JLabel chanLabel = new JLabel("CHAN");
		chanLabel.setBorder(new EmptyBorder(0,0,45,350));
		chanLabel.setFont(font);
		back.add(chanLabel);
		GridLayout portsLayout = new GridLayout(4,4);
		portsLayout.setHgap(15);
		JPanel ports = new JPanel(portsLayout);
		ports.setBorder(new EmptyBorder(0,70,0,70));
		ports.add(new JLabel("1"));
		ports.add(new JLabel("3"));
		ports.add(new JLabel("5"));
		ports.add(new JLabel("7"));
		for(int i = 0; i < 4; i++) { ports.add(new JLabel("" + (char)9675)); }
		ports.add(new JLabel("2"));
		ports.add(new JLabel("4"));
		ports.add(new JLabel("6"));
		ports.add(new JLabel("8"));
		for(int i = 0; i < 4; i++) { ports.add(new JLabel("" + (char)9675)); }

		back.add(ports);
		try {
			BufferedImage myPicture = ImageIO.read(new File("ChronoTimer/res/usbImage.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			back.add(picLabel);
		}catch(IOException e){}
		this.add(back, BorderLayout.SOUTH);

	}
	public class numPadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	public class JPanelPrintStream extends PrintStream
	{
		JTextPane printScreen;

		public JPanelPrintStream(JTextPane printScreen) {
			super(System.out,true);
			this.printScreen = printScreen;
		}
		@Override
		public void print(String s) {
			super.print(s);
			printScreen.setText(printScreen.getText()+s);
		}
		@Override
		public void println(String s) {
			printScreen.setText(printScreen.getText()+ s + "\n");
		}
	}
	public static void main(String[] args){
		new ChronoTimerUI();
	}
}


