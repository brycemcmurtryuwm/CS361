package com.haxorz.ChronoTimer.ui;

import com.haxorz.ChronoTimer.ChronoTimer;
import com.haxorz.ChronoTimer.Commands.*;
import com.haxorz.ChronoTimer.Races.Athlete;
import com.haxorz.ChronoTimer.Races.Race;
import com.haxorz.ChronoTimer.Races.RunRepository;
import com.haxorz.ChronoTimer.SystemClock;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.haxorz.ChronoTimer.Races.RunRepository.*;

/**
 * The UI used as a visual depiction of the chronoTimer
 */
public class ChronoTimerUI extends JFrame implements Observer{
	private ChronoTimer _timer;
	private JButton[] _numPad = new JButton[12];
	private JTextPane _screen;
	private JTextPane _printer = new JTextPane();
	private String _buffer = "";

	private boolean _coughed = false;
	private Random _randome = new Random();
	private static Executor _executor = Executors.newCachedThreadPool();

	private List<JComponent> _components = new ArrayList<>();
	private List<NumberedBox> _numberedBoxes = new ArrayList<>();

	private NumPadState _numPadState = NumPadState.NumCmd;
	private String _previousText = "";
	private RunningDisplayTimer _rdt;

	public ChronoTimerUI(){
		this.setSize(1000,900);
		this.setResizable(false);
		this.setTitle("ChronoTimer 1009");
		_timer = new ChronoTimer(new JPanelPrintStream(_printer));
		createComponents();
		RunRepository.getRunRepository().addObserver(this);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPoweredOn();
	}

	/**
	 * Builds the UI via Dark Magic...
	 * or Swing
	 */
	private void createComponents(){
		new JFXPanel();
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
		Collections.addAll(_numberedBoxes, enableBoxes);

		//printer
		JPanel printerPanel = new JPanel();
		JButton printerPower = new JButton("Printer Pwr");
		printerPower.addActionListener(e -> {_timer.executeCmd(new GenericCmd(CmdType.PRINTPWR, LocalTime.now()));
				_printer.setEnabled(_timer.isPrinterOn());
		});
		printerPanel.add(printerPower);

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

		JButton functionButton = new JButton("Function");
		functionButton.setFont(font);
		functionButton.addActionListener(new FunctionListener());
		functionButton.setAlignmentX(2);
		randomButtons.add(functionButton);

		JButton newRaceButton = new JButton("Create Race");
		newRaceButton.setFont(font);
		newRaceButton.addActionListener(new NewRaceListener(_timer));
		randomButtons.add(newRaceButton);

		randomButtons.add(new JLabel("                                                    "));	//filler
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));
		randomButtons.add(new JLabel("                                                    "));


		JButton swapButton= new JButton("Swap");
		swapButton.addActionListener(e -> _timer.executeCmd(new SwapCmd(LocalTime.now())));
		swapButton.setFont(font);
		randomButtons.add(swapButton);
		front.add(randomButtons);
		_components.add(randomButtons);

		//screenPanel
		JPanel screenPanel = new JPanel();
		_screen = new JTextPane();
		_screen.setEditable(false);
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
			switch (_numPadState){
				case NumCmd:
					if(Objects.equals(_buffer, ""))
						return;

					_timer.executeCmd(new NumCmd(LocalTime.now(), Integer.parseInt(_buffer)));
					_buffer = "";
					break;
				case AwaitingCancel:
					if(Objects.equals(_buffer, ""))
						return;

					_numPadState = NumPadState.NumCmd;
					_screen.setText(_previousText);
					_timer.executeCmd(new CancelCmd(LocalTime.now(), Integer.parseInt(_buffer)));
					_buffer = "";
					break;
				case AwaitingDNF:
					_numPadState = NumPadState.NumCmd;
					_screen.setText(_previousText);

					if(Objects.equals(_buffer, ""))
						_timer.executeCmd(new DNFCommand(LocalTime.now()));
					else _timer.executeCmd(new DNFCommand(LocalTime.now(), Integer.parseInt(_buffer)));
					_buffer = "";
					break;
				case AwaitingPrint:
					_numPadState = NumPadState.NumCmd;
					_screen.setText(_previousText);

					if(Objects.equals(_buffer, ""))
						_timer.executeCmd(new PrintCmd(LocalTime.now()));
					else _timer.executeCmd(new PrintCmd(LocalTime.now(), Integer.parseInt(_buffer)));
					_buffer = "";
					break;
				case AwaitingCLR:
					if(Objects.equals(_buffer, ""))
						return;

					_numPadState = NumPadState.NumCmd;
					_screen.setText(_previousText);
					_timer.executeCmd(new ClearCmd(LocalTime.now(), Integer.parseInt(_buffer)));
					_buffer = "";
					break;
				case AwaitingExport:
					_numPadState = NumPadState.NumCmd;
					_screen.setText(_previousText);

					if(Objects.equals(_buffer, ""))
						_timer.executeCmd(new ExportCmd(LocalTime.now()));
					else _timer.executeCmd(new ExportCmd(LocalTime.now(), Integer.parseInt(_buffer)));
					_buffer = "";
					break;
				case AwaitingTime:
					if(Objects.equals(_buffer, ""))
						return;

					try{
						LocalTime t = LocalTime.parse(getNumPadTime());

						_numPadState = NumPadState.NumCmd;
						_screen.setText(_previousText);
						_buffer = "";

						_timer.executeCmd(new TimeCmd(LocalTime.now(), t));
					}
					 catch(DateTimeParseException exp){
					}
					break;
			}
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
			box.addActionListener(new ConnectSensorListener(_timer));
			ports.add(box);
			_numberedBoxes.add(box);
		}
		ports.add(new JLabel("2"));
		ports.add(new JLabel("4"));
		ports.add(new JLabel("6"));
		ports.add(new JLabel("8"));
		for(int i = 1; i < 8; i+=2) {
			NumberedBox box = new NumberedBox(i + 1);
			box.addActionListener(new ConnectSensorListener(_timer));
			ports.add(box);
			_numberedBoxes.add(box);
		}
		back.add(ports);
		_components.add(ports);

		try {
			BufferedImage myPicture = ImageIO.read(new File("res/usbImage.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			back.add(picLabel);
			_components.add(picLabel);
			_rdt = new RunningDisplayTimer(_screen);
		}catch(IOException e){}
		this.add(back, BorderLayout.SOUTH);
	}

	private void tryPlaySoundFile(String bip){
		try {
			Media hit = new Media(new File(bip).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Turns on the UI
	 */
	private void setPoweredOn() {
		boolean isPoweredOn = _timer.isPoweredOn();

		for(JComponent jC: _components){
			setComponentsEnabled(jC, isPoweredOn);
		}

		_rdt._updateTimer.stop();
		_screen.setText(isPoweredOn ? "No Data To Display" : "");

		if(isPoweredOn)
			_executor.execute(() -> tryPlaySoundFile("res/dial_up.mp3"));

		if(isPoweredOn)
			_rdt._updateTimer.start();

		for(NumberedBox box: _numberedBoxes){
			box.setSelected(false);
		}

		_printer.setText("");
	}

	/**
	 * @param c the container whose components will be
	 *          enabled or disabled
	 * @param en whether the components shall be enabled
	 */
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
		if(_numPadState != NumPadState.NumCmd)
			return;

		_screen.setText(getRaceDisplayText());
	}

	/**
	 * @return a string depiction the current racers racing
	 */
	public static String getRaceDisplayText(){
		synchronized (Lock){
			if(InQueue.size()+Running.size()+Finalists.size() == 0 && RaceType != com.haxorz.ChronoTimer.Races.RaceType.GRP){
				return  "No Data To Display";
			}

			StringBuilder sb = new StringBuilder();

			if(InQueue.size()>0 || RaceType == com.haxorz.ChronoTimer.Races.RaceType.GRP){

				switch (RaceType){
					case IND:
						sb.append("In Queue: \n");
						for (int i = 0; i < 3 && i < InQueue.size(); i++) {
							sb.append(InQueue.get(i)).append("\n");
						}
						break;
					case PARIND:
						sb.append("In Queue: \n");
						for (int i = 0; i < 2 && i < InQueue.size(); i++) {
							sb.append(InQueue.get(i)).append("\n");
						}
						break;
					case GRP:
						sb.append("Running Time: ");
						if(GRPSTART != null)
							sb.append(SystemClock.toStringMinutes(Duration.between(GRPSTART, SystemClock.now()))).append("\n");
						break;
					case PARGRP:
						sb.append("In Queue: \n");
						for (int i = 0; i < 3 && i < InQueue.size(); i++) {
							sb.append(InQueue.get(i)).append("\n");
						}
						break;
				}
			}

			if(Running.size()>0){
				sb.append("Currently Running: \n");
				for (Athlete a: Running){
					sb.append("Athlete ").append(a.getNumber()).append(": ").append(a.getTimeTracker(Race.RunNumber).toStringMinutes()).append("\n");
				}
			}

			if(Finalists.size()>0){
				sb.append("Final Times: \n");

				for (int i = Finalists.size()-1; i >= 0 ; i--) {
					sb.append(Finalists.get(i)).append("\n");
				}
			}
			return sb.toString();
		}
	}


	private class numPadListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int num = _randome.nextInt(6);
			String toPlay = "res/numPad" + num + ".m4a";

			if(num == 0 || num == 5)
			{
				if(_coughed){
					toPlay = "res/numPad1.m4a";
				}
				else {
					_coughed = true;
					toPlay = "res/numPadSP.mp3";
				}
			}

			String finalToPlay = toPlay;
			_executor.execute(() -> tryPlaySoundFile(finalToPlay));

			JButton source = (JButton)e.getSource();
			_buffer += source.getText();

			if(_numPadState == NumPadState.AwaitingTime){
				_screen.setText("Use the numpad to set the Time. Press '#' to enter\n\t" + getNumPadTime());
			}
		}
	}

	private String getNumPadTime(){
		//7 places
		Queue<Integer> timeQueue = new LinkedList<>();
		Integer[] ints = new Integer[]{0,0,0,0,0,0,0};
		Collections.addAll(timeQueue,ints);

		for(char c: _buffer.toCharArray()){
			int i = Integer.parseInt(c + "");

			timeQueue.poll();
			timeQueue.add(i);
		}

		StringBuilder sb = new StringBuilder();
		int i = 0;

		while (!timeQueue.isEmpty()){
			if(++i == 3 || i == 5)
				sb.append(':');
			if(i==7)
				sb.append('.');
			sb.append(timeQueue.poll());
		}
		return sb.toString();
	}

	/**
	 * Asks what kind of function the user wants and then may use the num
	 * pad for number input. Then executes the command on the timer
	 */
	private class FunctionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			CmdType[] fuctions = {CmdType.RESET, CmdType.TIME, CmdType.DNF, CmdType.CANCEL, CmdType.START, CmdType.FINISH, CmdType.NEWRUN, CmdType.ENDRUN, CmdType.LOG, CmdType.PRINT, CmdType.CLR, CmdType.EXPORT};
			CmdType command = (CmdType) JOptionPane.showInputDialog(
					new JFrame(),
					"Select Command",
					"A Choice",
					JOptionPane.PLAIN_MESSAGE,
					null,
					fuctions,
					CmdType.CANCEL);

			if(command == null)
				return;

			CTCommand cmd;
			switch (command){
				case RESET:
					setPoweredOn();
				case LOG:
				case START:
				case FINISH:
				case NEWRUN:
				case ENDRUN:
					cmd = new GenericCmd(command, LocalTime.now());
					break;
				case TIME:
					_buffer = "";
					_previousText = _screen.getText();
					_screen.setText("Use the numpad to set the Time. Press '#' to enter\n\t00:00:00.0");
					_numPadState = NumPadState.AwaitingTime;
					return;
				case DNF:
					_previousText = _screen.getText();
					_screen.setText("Use the numpad to enter the Athlete Number to mark as DNF. Press '#' to enter");
					_numPadState = NumPadState.AwaitingDNF;
					return;
				case CANCEL:
					_previousText = _screen.getText();
					_screen.setText("Use the numpad to enter the Athlete Number to Cancel. Press '#' to enter");
					_numPadState = NumPadState.AwaitingCancel;
					return;
				case PRINT:
					_previousText = _screen.getText();
					_screen.setText("Use the numpad to enter the Race to Print. Press '#' to enter");
					_numPadState = NumPadState.AwaitingPrint;
					return;
				case CLR:
					_previousText = _screen.getText();
					_screen.setText("Use the numpad to enter the Athlete Number to Clear. Press '#' to enter");
					_numPadState = NumPadState.AwaitingCLR;
					return;
				case EXPORT:
					_previousText = _screen.getText();
					_screen.setText("Use the numpad to enter the Race to Export. Press '#' to enter");
					_numPadState = NumPadState.AwaitingExport;
					return;
				default:
					return;
			}
			_timer.executeCmd(cmd);
		}
	}



	public static void main(String[] args){

		new ChronoTimerUI();
	}
}



