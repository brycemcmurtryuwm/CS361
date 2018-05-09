package com.haxorz.ChronoTimer.ui;

import com.haxorz.ChronoTimer.ChronoTimer;
import com.haxorz.ChronoTimer.Commands.EventCmd;
import com.haxorz.ChronoTimer.Races.RaceType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

/**
 * An action listener that asks what kind of race the user
 * would like for the next run
 */
public class NewRaceListener implements ActionListener {

	private ChronoTimer _timer;

	public NewRaceListener(ChronoTimer timer){

		_timer = timer;
	}

	/**
	 * Creates a dialog asking what kind of race to do
	 *
	 * @param e the Action Event associated with this event
	 */
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
