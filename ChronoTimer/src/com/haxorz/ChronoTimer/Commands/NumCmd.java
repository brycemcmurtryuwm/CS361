package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class NumCmd extends CTCommand {

    public int Number;

    public NumCmd(LocalTime timeStamp, int number) {
        super(CmdType.NUM, timeStamp);
        Number = number;
    }

}
