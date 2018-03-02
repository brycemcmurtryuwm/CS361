package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GenericCmd extends CTCommand {

    public GenericCmd(CmdType type, LocalTime timeStamp) {
        super(type, timeStamp);
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType;
    }

}
