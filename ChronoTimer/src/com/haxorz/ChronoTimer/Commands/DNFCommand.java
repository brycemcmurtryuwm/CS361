package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DNFCommand extends CTCommand {
	int athleteNumber;

	public DNFCommand(LocalTime timeStamp, int a) {
		super(CmdType.DNF, timeStamp);
		this.athleteNumber = a;
	}

	public int getAthleteNumber() {
		return athleteNumber;
	}

	@Override
	public String ToString() {
		return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + athleteNumber;
	}
}
