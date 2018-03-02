package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class TimeCmd extends CTCommand {

    public LocalTime TimeToSet;

    public TimeCmd(LocalTime timeStamp, LocalTime timeToSet) {
        super(CmdType.TIME, timeStamp);
        this.TimeToSet = timeToSet;
    }

    @Override
    public String ToString() {
        return this.TimeStamp + " " + this.CMDType + " " + TimeToSet;
    }
}
