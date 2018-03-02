package com.haxorz.ChronoTimer.Commands;

import com.haxorz.ChronoTimer.Races.RaceType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventCmd extends CTCommand {
    public com.haxorz.ChronoTimer.Races.RaceType RaceType;

    public EventCmd(LocalTime timeStamp, RaceType raceType) {
        super(CmdType.EVENT, timeStamp);
        RaceType = raceType;
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + RaceType;
    }
}
