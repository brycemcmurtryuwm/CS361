package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.*;
import com.haxorz.ChronoTimer.Hardware.Channel;
import com.haxorz.ChronoTimer.Hardware.InputSensor;
import com.haxorz.ChronoTimer.Hardware.Printer;
import com.haxorz.ChronoTimer.Races.IndividualRace;
import com.haxorz.ChronoTimer.Races.Race;
import com.haxorz.ChronoTimer.Races.RunRepository;

import java.io.PrintStream;

public class ChronoTimer {

    private boolean poweredOn = false;

    private Race currentRace = new IndividualRace();

    private Channel[] channels = new Channel[12];
    private InputSensor[] sensors = new InputSensor[12];

    private Printer printer;

    public ChronoTimer(PrintStream out) {
        for (int i = 0; i < 12; i++) {
            channels[i] = new Channel(i+1);
        }

        printer = new Printer(out);
        Channel.ChannelListener = currentRace;
    }

    private void Reset() {
        Race.COMPETITORS.clear();
        currentRace = new IndividualRace();
        sensors = new InputSensor[12];

        for (int i = 0; i < 12; i++) {
            channels[i] = new Channel(i+1);
        }

        Channel.ChannelListener = currentRace;
    }

    public void executeCmd(CTCommand cmd){
        printer.log(cmd);

        if(cmd.CMDType == CmdType.POWER){
            poweredOn = !poweredOn;
            return;
        }

        if(!poweredOn)
        {
            //TODO MAKE SURE ALL DATA RESET IF POWERED OFF !!!!!!!!!!!!!!!!!!
            Reset();
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
                        //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                        break;
                    case GRP:
                        //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                        break;
                    case PARGRP:
                        //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                        break;
                }

                Channel.ChannelListener = currentRace;
                return;
            case EXIT:
                Simulator.EXIT = true;
                return;
            case RESET:
                Reset();
                break;
            case EXPORT:
                //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                break;
            case PRINT:
                PrintCmd printCmd = (PrintCmd)cmd;

                if(!RunRepository.CompletedRuns.containsKey(printCmd.RaceNumber))
                    return;
                printer.print(RunRepository.CompletedRuns.get(printCmd.RaceNumber));
                break;
            case TIME:
                TimeCmd time = (TimeCmd)cmd;

                //TODO SET TIME!!!!!!!!!!!!!!!!!!!
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

            case TOG:
                ToggleCmd tog = (ToggleCmd) cmd;
                channels[tog.Channel-1].ToggleChannel();
                break;
            case TRIG:
                TriggerCmd trig = (TriggerCmd) cmd;

                InputSensor sensor = sensors[trig.Channel -1];

                if(sensor != null)
                    sensor.Triggered(cmd.TimeStamp);
                else
                    channels[trig.Channel - 1].Trigger(cmd.TimeStamp);
                break;
            case START:
                sensor = sensors[0];

                if(sensor != null)
                    sensor.Triggered(cmd.TimeStamp);
                else
                    channels[0].Trigger(cmd.TimeStamp);
                break;
            case FINISH:
                sensor = sensors[1];

                if(sensor != null)
                    sensor.Triggered(cmd.TimeStamp);
                else
                    channels[1].Trigger(cmd.TimeStamp);
                break;
            case DNF:
            case NEWRUN:
            case ENDRUN:
            case NUM:
            case CLR:
            default:
                if(currentRace != null){
                    currentRace.executeCmd(cmd);
                }
        }



    }


}
