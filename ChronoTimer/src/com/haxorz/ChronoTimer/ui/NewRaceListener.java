package com.haxorz.ChronoTimer.ui;

import com.haxorz.ChronoTimer.ChronoTimer;
import com.haxorz.ChronoTimer.Commands.EventCmd;
import com.haxorz.ChronoTimer.Races.RaceType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class NewRaceListener implements ActionListener {

	private ChronoTimer _timer;

	public NewRaceListener(ChronoTimer timer){

		_timer = timer;
	}

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
		if(newRace != null)
			_timer.executeCmd(new EventCmd(LocalTime.now(), newRace));
	}
}
