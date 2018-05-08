package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Exports the race with the given number.
 * If no race is specified, the current race will be used
 */
public class ExportCmd extends CTCommand {

    public int RaceNumber;
    public boolean UseCurrentRun;

    public ExportCmd(LocalTime timeStamp, int raceNumber) {
        super(CmdType.EXPORT, timeStamp);
        RaceNumber = raceNumber;
    }

    public ExportCmd(LocalTime timeStamp) {
        super(CmdType.EXPORT, timeStamp);
        UseCurrentRun = true;
    }


    @Override
    public String ToString() {
        if(UseCurrentRun)
            return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType;

        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + RaceNumber;
    }
}
