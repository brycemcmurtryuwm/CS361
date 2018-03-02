package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class PrintCmd extends CTCommand {

    public int RaceNumber;

    public PrintCmd(LocalTime timeStamp, int raceNumber) {
        super(CmdType.PRINT, timeStamp);
        RaceNumber = raceNumber;
    }


    @Override
    public String ToString() {
        return this.TimeStamp + " " + this.CMDType + " " + RaceNumber;
    }
}
