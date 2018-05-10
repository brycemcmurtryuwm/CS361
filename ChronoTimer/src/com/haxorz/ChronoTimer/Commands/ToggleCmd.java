package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Toggles the channel specified
 */
public class ToggleCmd extends CTCommand {

    public int Channel;

    public ToggleCmd(LocalTime timeStamp, int channel) {
        super(CmdType.TOG, timeStamp);
        this.Channel = channel;
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + Channel;
    }
}
