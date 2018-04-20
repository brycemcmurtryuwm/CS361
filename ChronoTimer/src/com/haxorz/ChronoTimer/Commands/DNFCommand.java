package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DNFCommand extends CTCommand {
	private int lane;

	public DNFCommand(LocalTime timeStamp, int lane) {
		super(CmdType.DNF, timeStamp);
		this.lane = lane;
	}
	public DNFCommand(LocalTime timeStamp) {
		this(timeStamp, 1);
	}
	public int getLane() {
		return lane;
	}

	@Override
	public String ToString() {
		return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + lane;
	}
}
