package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.*;
import com.haxorz.ChronoTimer.Hardware.Channel;
import com.haxorz.ChronoTimer.Hardware.Export;
import com.haxorz.ChronoTimer.Hardware.InputSensor;
import com.haxorz.ChronoTimer.Hardware.Printer;
import com.haxorz.ChronoTimer.Races.*;
import com.haxorz.ChronoTimer.Web.Client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class ChronoTimer {


    private boolean poweredOn = false;
    private Race currentRace = new IndividualRace();
    private List<Observer> observers = new ArrayList<>();

    //simulated components of the chronoTimer
    private Channel[] channels = new Channel[12];
    private InputSensor[] sensors = new InputSensor[12];
    private Printer printer;

    public ChronoTimer(PrintStream out) {
        for (int i = 0; i < 12; i++) {
            channels[i] = new Channel(i+1);
        }

        printer = new Printer(out);
        Channel.ChannelListener = currentRace;
        setRaceObserver(RunRepository.getRunRepository());
        RunRepository.getRunRepository().addObserver(new Client());
    }

    /**
     * used when keeping a live count of the current racers
     * (as is done in the UI)
     * @param o the observer being set
     */
    //
    public void setRaceObserver(Observer o){
        currentRace.addObserver(o);
        observers.add(o);
    }

    /**
     * resets the timing system and removes all tracked races
     */
    private void reset() {
        Race.COMPETITORS.clear();
        Race.RunNumber = 0;
        RunRepository.clear();
        currentRace.deleteObservers();
        currentRace = new IndividualRace();
        sensors = new InputSensor[12];

        for (int i = 0; i < 12; i++) {
            channels[i] = new Channel(i+1);
        }

        setObservers();
        Channel.ChannelListener = currentRace;
    }

    /**
     * Takes a command and fullfils that command on
     * our chronoTimer
     *
     * @param cmd Command parsed from CTCommand
     */
    public void executeCmd(CTCommand cmd){
        if(cmd == null)
            return;

        if(cmd.CMDType == CmdType.POWER){
            poweredOn = !poweredOn;
        }

        if(!poweredOn)
        {
            reset();
            return;
        }

        printer.log(cmd);

        switch (cmd.CMDType){
            case EVENT:
                EventCmd race = (EventCmd)cmd;
                currentRace.deleteObservers();

                switch (race.RaceType){
                    case IND:
                        currentRace = new IndividualRace();
                        break;
                    case PARIND:
                        currentRace = new ParIndRace();
                        break;
                    case GRP:
                        currentRace = new GrpRace();
                        break;
                    case PARGRP:
                        currentRace = new ParGrpRace();
                        break;
                }

                setObservers();
                Channel.ChannelListener = currentRace;
                return;
            case EXIT:
                Simulator.EXIT = true;
                return;
            case RESET:
                reset();
                break;
            case EXPORT:
                ExportCmd exportCmd = (ExportCmd)cmd;

                if(exportCmd.UseCurrentRun){
                    Export.SaveRunToFile(Race.RunNumber);
                    return;
                }

                if(!RunRepository.CompletedRuns.containsKey(exportCmd.RaceNumber))
                    return;
                Export.SaveRunToFile(exportCmd.RaceNumber);
                break;
            case PRINTPWR:
                printer.PowerPushed(cmd.TimeStamp);
                break;
            case PRINT:
                PrintCmd printCmd = (PrintCmd)cmd;
                if(printCmd.UseCurrentRun){
                    printer.print(RunRepository.getCurrentRun());
                    return;
                }
                if(!RunRepository.CompletedRuns.containsKey(printCmd.RaceNumber)) return;
                printer.print(RunRepository.CompletedRuns.get(printCmd.RaceNumber));
                break;
            case TIME:
                TimeCmd time = (TimeCmd)cmd;

                SystemClock.setNow(time.TimeToSet);
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

            //commands that will be passed to the race
            case DNF:
            case NEWRUN:
            case ENDRUN:
            case NUM:
            case CLR:
            default:
                //passes command to race, assuming it exists
                if(currentRace != null){
                    currentRace.executeCmd(cmd);
                }
        }
    }

    public boolean isPoweredOn(){
        return poweredOn;
    }

    /**
     * sets all of the race's observers no observe
     * the current race
     */
    private void setObservers() {
        for(Observer o: observers){
            currentRace.addObserver(o);
        }
    }


}
