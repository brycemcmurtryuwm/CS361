package com.haxorz.ChronoTimer.ui;

import com.haxorz.ChronoTimer.ChronoTimer;
import com.haxorz.ChronoTimer.Commands.*;
import com.haxorz.ChronoTimer.Hardware.SensorType;
import com.haxorz.ChronoTimer.Races.Race;
import com.haxorz.ChronoTimer.Races.RaceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ChronoTimerUI extends JFrame implements Observer{
	private ChronoTimer _timer;
	private JButton[] _numPad = new JButton[12];
	private JTextPane _screen;
	private JTextPane _printer;
	private String _buffer = "";

	private List<JComponent> _components = new ArrayList<>();

	public ChronoTimerUI(){
		this.setSize(1000,850);
		this.setResizable(false);
		this.setTitle("ChronoTimer 1009");
		createComponents();
		_timer = new ChronoTimer(new JPanelPrintStream(_printer));
		_timer.setRaceObserver(this);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPoweredOn();
	}

	private void createComponents(){
		JPanel front = new JPanel(new GridLayout(2,3));

		Font font = new Font("SansSerif", Font.PLAIN, 20);
		Font font2 = new Font("SansSerif", Font.PLAIN, 40);
		Font font3 = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
		setFont(font);


		//powerButton
		JPanel powerPanel = new JPanel();
		JButton powerButton = new JButton("Power");
		powerButton.setFont(font);
		powerButton.addActionListener(e -> {_timer.executeCmd(new GenericCmd(CmdType.POWER, LocalTime.now()));
			setPoweredOn();
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
		NumberedBox[] enableBoxes = new NumberedBox[8];


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
			startButtons[i].addActionListener(e -> _timer.executeCmd(new TriggerCmd(LocalTime.now(), finalI + 1)));
			sensorGrid.add(startButtons[i]);
		}
		sensorGrid.add(enableLabel1);
		for(int i = 0; i < 8; i+=2) {
			enableBoxes[i] = new NumberedBox(i+1);
			enableBoxes[i].addActionListener(e -> {
                NumberedBox source = (NumberedBox)e.getSource();
                _timer.executeCmd(new ToggleCmd(LocalTime.now(), source.channel));
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
			startButtons[i].addActionListener(e -> _timer.executeCmd(new TriggerCmd(LocalTime.now(), finalI + 1)));
			sensorGrid.add(startButtons[i]);
		}
		sensorGrid.add(enableLabel2);
		for(int i = 1; i < 8; i+=2) {
			enableBoxes[i] = new NumberedBox(i + 1);
			sensorGrid.add(enableBoxes[i]);
			enableBoxes[i].addActionListener(e -> {
                NumberedBox source = (NumberedBox)e.getSource();
                _timer.executeCmd(new ToggleCmd(LocalTime.now(), source.channel));
            });
		}
		channelsPanel.add(sensorGrid);
		front.add(channelsPanel);
		_components.add(channelsPanel);

		//printer
		JPanel printerPanel = new JPanel();
		JButton printerPower = new JButton("Printer Pwr");
		printerPower.addActionListener(e -> _timer.executeCmd(new GenericCmd(CmdType.PRINTPWR, LocalTime.now())));
		printerPanel.add(printerPower);
		_printer = new JTextPane();
		_printer.setEditable(false);
		JScrollPane printerScroll = new JScrollPane(_printer);
		_printer.setPreferredSize(new Dimension(250,280));
		_printer.setMaximumSize(new Dimension(250,280));
		printerPanel.add(printerScroll);
		front.add(printerPanel);
		_components.add(printerPanel);

		//Function, arrows, and Swap
		JPanel randomButtons = new JPanel();
		randomButtons.setBorder(new EmptyBorder(0,50,0,50));
		/*
		//as it is now the function and arrows have no purpose
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
		randomButtons.add(arrows);*/
		JButton newRaceButton = new JButton("Create Race");
		newRaceButton.setFont(font);
		newRaceButton.addActionListener(new NewRaceListener());
		randomButtons.add(newRaceButton);

		randomButtons.add(new JLabel("                                                    "));	//filler
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));


		JButton swapButton= new JButton("Swap");
		//TODO Test
		swapButton.addActionListener(e -> _timer.executeCmd(new SwapCmd(LocalTime.now())));
		swapButton.setFont(font);
		randomButtons.add(swapButton);
		front.add(randomButtons);
		_components.add(randomButtons);

		//screenPanel
		JPanel screenPanel = new JPanel();
		_screen = new JTextPane();
		_screen.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		_screen.setPreferredSize(new Dimension(250,280));
		_screen.setMaximumSize(new Dimension(250,280));
		_screen.setText("No Data To Display");
		JScrollPane screenScroll = new JScrollPane(_screen);
		screenPanel.add(screenScroll);
		JLabel screenInfo = new JLabel("Queue/Running/Final Time");
		screenInfo.setFont(font);
		screenPanel.add(screenInfo, BorderLayout.SOUTH);
		front.add(screenPanel, BorderLayout.CENTER);
		_components.add(screenPanel);

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
		//This has not purpose except to cause Parse Errors?
		//_numPad[10].addActionListener(new numPadListener());
		numPanel.add(_numPad[10]);
		_numPad[0] = new JButton("0");
		_numPad[0].setFont(font2);
		_numPad[0].addActionListener(new numPadListener());
		numPanel.add(_numPad[0]);
		_numPad[11] = new JButton("#");
		_numPad[11].setFont(font2);
		_numPad[11].addActionListener(e -> {
            _timer.executeCmd(new NumCmd(LocalTime.now(), Integer.parseInt(_buffer)));
            _buffer = "";
        });

		numPanel.add(_numPad[11]);
		JPanel numPanelCover = new JPanel(new FlowLayout());
		numPanelCover.add(numPanel);
		front.add(numPanelCover,BorderLayout.EAST);
		_components.add(numPanelCover);
		this.add(front, BorderLayout.CENTER);



		//the back
		JPanel back = new JPanel();
		JLabel chanLabel = new JLabel("CHAN");
		chanLabel.setBorder(new EmptyBorder(0,0,45,350));
		chanLabel.setFont(font);
		back.add(chanLabel);
		_components.add(chanLabel);

		GridLayout portsLayout = new GridLayout(4,4);
		portsLayout.setHgap(15);
		JPanel ports = new JPanel(portsLayout);
		ports.setBorder(new EmptyBorder(0,70,0,70));
		ports.add(new JLabel("1"));
		ports.add(new JLabel("3"));
		ports.add(new JLabel("5"));
		ports.add(new JLabel("7"));
		for(int i = 0; i < 8; i+=2) {
			NumberedBox box = new NumberedBox(i + 1);
			box.addActionListener(new connectSensorListener());
			ports.add(box);
		}
		ports.add(new JLabel("2"));
		ports.add(new JLabel("4"));
		ports.add(new JLabel("6"));
		ports.add(new JLabel("8"));
		for(int i = 1; i < 8; i+=2) {
			NumberedBox box = new NumberedBox(i + 1);
			box.addActionListener(new connectSensorListener());
			ports.add(box);
		}
		back.add(ports);
		_components.add(ports);

		try {
			BufferedImage myPicture = ImageIO.read(new File("ChronoTimer/res/usbImage.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			back.add(picLabel);
			_components.add(picLabel);
		}catch(IOException e){}
		this.add(back, BorderLayout.SOUTH);
	}

	private void setPoweredOn() {
		boolean isPoweredOn = _timer.isPoweredOn();

		for(JComponent jC: _components){
			setComponentsEnabled(jC, isPoweredOn);
		}

		_screen.setText(isPoweredOn ? "No Data To Display" : "");
		_printer.setText("");
	}

	private void setComponentsEnabled(java.awt.Container c, boolean en) {
		Component[] components = c.getComponents();
		for (Component comp: components) {
			if (comp instanceof java.awt.Container)
				setComponentsEnabled((java.awt.Container) comp, en);
			comp.setEnabled(en);
		}
		c.setEnabled(en);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Race){
			StringBuilder sb = new StringBuilder();

			List<String> finalists = ((Race) o).getCompletedTimes();
			List<String> running = ((Race) o).getAthletesRunning();
			List<String> inQueue = ((Race) o).getAthletesInQueue();

			if(finalists.size()>0){
				sb.append("Final Times: \n");
				for (String s: finalists){
					sb.append(s).append("\n");
				}
			}

			if(running.size()>0){
				sb.append("Currently Running: \n");
				for (String s: running){
					sb.append(s).append("\n");
				}
			}

			if(inQueue.size()>0){
				sb.append("In Queue: \n");
				for (String s: inQueue){
					sb.append(s).append("\n");
				}
			}

			if(inQueue.size()+running.size()+finalists.size() == 0){
				_screen.setText("No Data To Display");
				return;
			}
			_screen.setText(sb.toString());
		}
	}


	public class numPadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton)e.getSource();
			_buffer += source.getText();
		}
	}
	public class NewRaceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			RaceType newRace = (RaceType) JOptionPane.showInputDialog(
					new JFrame(),
					"Select Race Type",
					"A Choice",
					JOptionPane.PLAIN_MESSAGE,
					null,
					RaceType.values(),
					RaceType.IND);
			_timer.executeCmd(new EventCmd(LocalTime.now(), newRace));
		}
	}

	public class connectSensorListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			NumberedBox source = (NumberedBox)e.getSource();
			if(source.isSelected()){
				SensorType s = (SensorType) JOptionPane.showInputDialog(
						new JFrame(),
						"Select Sensor Type",
						"A Choice",
						JOptionPane.PLAIN_MESSAGE,
						null,
						SensorType.values(),
						SensorType.EYE);
				if(s == null)
					return;
				_timer.executeCmd(new ConnectCmd(s,source.channel,LocalTime.now()));
			}
			else{
				_timer.executeCmd(new DisconnectCmd(LocalTime.now(),source.channel));
			}
		}
	}

	public static void main(String[] args){
		

		new ChronoTimerUI();
	}
}





