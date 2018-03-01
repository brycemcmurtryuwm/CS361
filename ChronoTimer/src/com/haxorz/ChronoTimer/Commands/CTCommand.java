package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public abstract class CTCommand {

    public CmdType CMDType;
    public LocalTime TimeStamp;

    public CTCommand(CmdType type, LocalTime timeStamp){
        CMDType = type;
        this.TimeStamp = timeStamp;
    }

}
