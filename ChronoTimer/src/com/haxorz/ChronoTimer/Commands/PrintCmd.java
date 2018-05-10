package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Prints the string of the race with a given number.
 * If no race is specified the current race will be used
 */
public class PrintCmd extends CTCommand {

    public int RaceNumber;
    public boolean UseCurrentRun;

    public PrintCmd(LocalTime timeStamp, int raceNumber) {
        super(CmdType.PRINT, timeStamp);
        RaceNumber = raceNumber;
    }

    public PrintCmd(LocalTime timeStamp) {
        super(CmdType.PRINT, timeStamp);
        UseCurrentRun = true;
    }


    @Override
    public String ToString() {
        if(UseCurrentRun)
            return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType;

        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + RaceNumber;
    }
}
