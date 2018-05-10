package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * deletes the athlete with the given number
 */
public class ClearCmd extends CTCommand {

    public int Num;

    public ClearCmd(LocalTime timeStamp, int num) {
        super(CmdType.CLR, timeStamp);
        Num = num;
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + Num;
    }
}
