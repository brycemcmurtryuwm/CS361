package com.haxorz.ChronoTimer.Commands;

import com.haxorz.ChronoTimer.Races.RaceType;

import java.time.LocalTime;

public class EventCmd extends CTCommand {
    public com.haxorz.ChronoTimer.Races.RaceType RaceType;

    public EventCmd(LocalTime timeStamp, RaceType raceType) {
        super(CmdType.EVENT, timeStamp);
        RaceType = raceType;
    }

    @Override
    public String ToString() {
        return this.TimeStamp + " " + this.CMDType + " " + RaceType;
    }
}
