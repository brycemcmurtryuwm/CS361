package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class DisconnectCmd extends CTCommand {

    public int Channel;

    public DisconnectCmd(LocalTime timeStamp, int channel) {
        super(CmdType.DISC, timeStamp);
        Channel = channel;
    }

}
