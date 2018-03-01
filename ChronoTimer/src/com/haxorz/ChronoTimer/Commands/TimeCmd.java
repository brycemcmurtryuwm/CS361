package com.haxorz.ChronoTimer.Commands;

import java.sql.Timestamp;
import java.time.LocalTime;

public class TimeCmd extends CTCommand {

    public Timestamp TimeToSet;

    public TimeCmd(LocalTime timeStamp, Timestamp timeToSet) {
        super(CmdType.TIME, timeStamp);
        this.TimeToSet = timeToSet;
    }
}
