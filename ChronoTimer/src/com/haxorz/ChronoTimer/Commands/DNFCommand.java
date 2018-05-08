package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The next athlete to finish in the given lane will not finish.
 * If no lane is defined it will assume lane one.
 */
public class DNFCommand extends CTCommand {
	private int lane;

	/**
	 * The next athlete to finish in the given lane will not finish.
	 * @param timeStamp time command is placed
	 * @param lane the lane
	 */
	public DNFCommand(LocalTime timeStamp, int lane) {
		super(CmdType.DNF, timeStamp);
		this.lane = lane;
	}

	/**
	 * The next athlete to finish in lane one will be marked as DNF
	 *
	 * @param timeStamp the time command is placed
	 */
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
