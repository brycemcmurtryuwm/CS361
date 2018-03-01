package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class GenericCmd extends CTCommand {

    public GenericCmd(CmdType type, LocalTime timeStamp) {
        super(type, timeStamp);
    }

}
