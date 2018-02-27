package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Races.Race;

public class ChronoTimer {

    private boolean poweredOn = false;

    private Race currentRace = null;

    public void executeCmd(CTCommand cmd){

        if(cmd.CMDType == CmdType.POWER){
            poweredOn = !poweredOn;
        }

        if(!poweredOn)
        {
            //TODO MAKE SURE ALL DATA RESET IF POWERED OFF
            return;
        }


        //able to set race


        if(currentRace != null){
            currentRace.executeCmd(cmd);
        }

    }
}
