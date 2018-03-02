package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DisconnectCmd extends CTCommand {

    public int Channel;

    public DisconnectCmd(LocalTime timeStamp, int channel) {
        super(CmdType.DISC, timeStamp);
        Channel = channel;
    }


    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + Channel;
    }
}
