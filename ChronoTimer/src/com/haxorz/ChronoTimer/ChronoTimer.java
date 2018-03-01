package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Races.IndividualRace;
import com.haxorz.ChronoTimer.Races.Race;

public class ChronoTimer {

    private boolean poweredOn = false;

    private Race currentRace = null;

    public void executeCmd(CTCommand cmd){

        if(cmd.CMDType == CmdType.POWER){
            poweredOn = !poweredOn;
            return;
        }

        if(!poweredOn)
        {
            //TODO MAKE SURE ALL DATA RESET IF POWERED OFF
            return;
        }

        switch (cmd.CMDType){
            case EVENT:
                Race race = (Race)cmd;

                switch (race.getRaceType()){
                    case GRP:
                        break;
                    case IND:
                        currentRace = new IndividualRace();
                        break;
                }
                return;
            case EXIT:
                //TODO EXIT
                return;
            case TIME:
                break;
            case CONN:
                break;
            default:
                if(currentRace != null){
                    currentRace.executeCmd(cmd);
                }
        }




    }
}
