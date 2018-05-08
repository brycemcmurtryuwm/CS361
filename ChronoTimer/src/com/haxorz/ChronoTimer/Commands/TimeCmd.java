package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * sets the system time to the time specified
 */
public class TimeCmd extends CTCommand {

    public LocalTime TimeToSet;

    public TimeCmd(LocalTime timeStamp, LocalTime timeToSet) {
        super(CmdType.TIME, timeStamp);
        this.TimeToSet = timeToSet;
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + TimeToSet;
    }
}
