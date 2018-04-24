package com.haxorz.ChronoTimer.ui;

import com.haxorz.ChronoTimer.ChronoTimer;
import com.haxorz.ChronoTimer.Commands.ConnectCmd;
import com.haxorz.ChronoTimer.Commands.DisconnectCmd;
import com.haxorz.ChronoTimer.Hardware.SensorType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class ConnectSensorListener implements ActionListener {

	private final ChronoTimer _timer;

	public ConnectSensorListener(ChronoTimer timer){

		_timer = timer;
	}

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
			{
				source.setSelected(false);
				return;
			}
			_timer.executeCmd(new ConnectCmd(s,source.channel, LocalTime.now()));
		}
		else{
			_timer.executeCmd(new DisconnectCmd(LocalTime.now(),source.channel));
		}
	}
}
