package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class NumCmd extends CTCommand {

    public int Number;

    public NumCmd(LocalTime timeStamp, int number) {
        super(CmdType.NUM, timeStamp);
        Number = number;
    }


    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + Number;
    }
}
