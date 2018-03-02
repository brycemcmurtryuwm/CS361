package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class ClearCmd extends CTCommand {

    public int Num;

    public ClearCmd(LocalTime timeStamp, int num) {
        super(CmdType.CLR, timeStamp);
        Num = num;
    }

}
