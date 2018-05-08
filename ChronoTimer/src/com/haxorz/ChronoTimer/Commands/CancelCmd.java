package com.haxorz.ChronoTimer.Commands;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
//this command cancels whatever athlete is specified in the command
public class CancelCmd extends CTCommand {

    public int AthleteNum;

    public CancelCmd(LocalTime timeStamp, int athleteNum) {
        super(CmdType.CANCEL, timeStamp);
        AthleteNum = athleteNum;
    }

    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + AthleteNum;
    }

}
