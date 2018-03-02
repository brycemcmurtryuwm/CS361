package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class ToggleCmd extends CTCommand {

    public int Channel;

    public ToggleCmd(LocalTime timeStamp, int channel) {
        super(CmdType.TOG, timeStamp);
        this.Channel = channel;
    }

    @Override
    public String ToString() {
        return this.TimeStamp + " " + this.CMDType + " " + Channel;
    }
}
