package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CmdType;

public abstract class Race extends CTCommand {

    public Race() {
        super(CmdType.EVENT);
    }

    public  abstract RaceType getRaceType();

    public abstract void executeCmd(CTCommand cmd);
}
