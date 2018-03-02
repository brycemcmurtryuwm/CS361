package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;

public class CancelCmd extends CTCommand {

    public int AthleteNum;

    public CancelCmd(LocalTime timeStamp, int athleteNum) {
        super(CmdType.CANCEL, timeStamp);
        AthleteNum = athleteNum;
    }

}
