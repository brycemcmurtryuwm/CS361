package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class TriggerCmd extends CTCommand {

    public int Channel;

    public TriggerCmd(LocalTime timeStamp, int channel) {
        super(CmdType.TRIG, timeStamp);
        Channel = channel;
    }

}
