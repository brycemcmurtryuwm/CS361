package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Hardware.HWEventType;
import com.haxorz.ChronoTimer.Hardware.HardwareEventListener;

public abstract class Race {

    public  abstract RaceType getRaceType();

    public abstract void executeCmd(CTCommand cmd);

}
