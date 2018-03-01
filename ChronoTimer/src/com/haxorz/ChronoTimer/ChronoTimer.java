package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.*;
import com.haxorz.ChronoTimer.Hardware.Channel;
import com.haxorz.ChronoTimer.Hardware.InputSensor;
import com.haxorz.ChronoTimer.Races.IndividualRace;
import com.haxorz.ChronoTimer.Races.Race;

public class ChronoTimer {

    private boolean poweredOn = false;

    private Race currentRace = null;

    private Channel[] channels = new Channel[12];
    private InputSensor[] sensors = new InputSensor[12];

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
                EventCmd race = (EventCmd)cmd;

                switch (race.RaceType){
                    case IND:
                        currentRace = new IndividualRace();
                        break;
                    case PARIND:
                        //TODO IN THE FUTURE
                        break;
                    case GRP:
                        //TODO IN THE FUTURE
                        break;
                    case PARGRP:
                        //TODO IN THE FUTURE
                        break;
                }
                return;
            case EXIT:
                //TODO EXIT
                return;
            case RESET:
                break;
            case CANCEL:
                break;
            case PRINT:
                break;
            case TIME:
                TimeCmd time = (TimeCmd)cmd;

                //TODO SET TIME
                break;
            case CONN:
                ConnectCmd conn = (ConnectCmd)cmd;
                sensors[conn.channel-1] = new InputSensor(conn.Sensor);
                sensors[conn.channel-1].connect(channels[conn.channel-1]);
                break;
            case DISC:
                DisconnectCmd disc = (DisconnectCmd)cmd;
                sensors[disc.Channel-1].disconnect();
                sensors[disc.Channel-1] = null;
                break;
            default:
                if(currentRace != null){
                    currentRace.executeCmd(cmd);
                }
        }




    }


}
