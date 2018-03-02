package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Hardware.ChannelListener;
import com.haxorz.ChronoTimer.Hardware.HWEventType;
import com.haxorz.ChronoTimer.Hardware.HardwareEventListener;

import java.util.HashMap;

public abstract class Race implements ChannelListener{

    public static final HashMap<Integer,Athlete> COMPETITORS = new HashMap<>();

    public int RunNumber = 1;

    public abstract RaceType getRaceType();

    public abstract void executeCmd(CTCommand cmd);

}
