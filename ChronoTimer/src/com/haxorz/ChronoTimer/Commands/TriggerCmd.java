package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TriggerCmd extends CTCommand {

    public int Channel;

    public TriggerCmd(LocalTime timeStamp, int channel) {
        super(CmdType.TRIG, timeStamp);
        Channel = channel;
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + Channel;
    }

}
