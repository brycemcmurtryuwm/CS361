package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This command is used then a later racer outruns an earlier one.
 * Used in Individual and Parallel Individual Race types.
 *
 * Will assume channel one unless specified
 *
 */
public class SwapCmd extends CTCommand{

    public int ChannelNum;

    public SwapCmd(LocalTime timeStamp, int channelNum) {
        super(CmdType.SWAP, timeStamp);
        ChannelNum = channelNum;
    }

    public SwapCmd(LocalTime timeStamp) {
        super(CmdType.SWAP, timeStamp);
        ChannelNum = 1;
    }


    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + ChannelNum;
    }
}
