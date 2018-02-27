package com.haxorz.ChronoTimer.Commands;

public abstract class CTCommand {

    public CmdType CMDType;

    public CTCommand(CmdType type){
        CMDType = type;
    }

}
