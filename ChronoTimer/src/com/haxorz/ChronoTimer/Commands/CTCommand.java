package com.haxorz.ChronoTimer.Commands;

import com.haxorz.ChronoTimer.Hardware.SensorType;
import com.haxorz.ChronoTimer.Races.RaceType;
import com.haxorz.ChronoTimer.SystemClock;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public abstract class CTCommand {

    public CmdType CMDType;
    public LocalTime TimeStamp;

    public CTCommand(CmdType type, LocalTime timeStamp){
        CMDType = type;
        this.TimeStamp = timeStamp;
    }

    /**
     * This takes in a string with an optional <TIMESTAMP> and <Command>
     * @param toParse
     * @return a CTCommand based on @toParse
     */
    public static CTCommand ParseFromString(String toParse){
        String[] tmpArr = toParse.trim().split("\\s+");

        LocalTime t = SystemClock.now();
        boolean firstArgTime = true;

        //this tells us if it is a legal time
        //according to ISO local time, which
        //it appeared to be in the PDF
        try{
            t = LocalTime.parse(tmpArr[0]);
        } catch(DateTimeParseException e){
            firstArgTime = false;
        }

        //this reduces the size of the array since we don't have to deal with the time anymore
        tmpArr = firstArgTime ? Arrays.copyOfRange(tmpArr, 1,tmpArr.length) : tmpArr;

        switch (tmpArr[0].toLowerCase()){
            case "power":
                return new GenericCmd(CmdType.POWER, t);
            case "tog":
                int channel;

                if(tmpArr.length < 2)
                    return null;

                try {
                    channel = Integer.parseInt(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new ToggleCmd(t, channel);
            case "exit":
                return new GenericCmd(CmdType.EXIT, t);
            case "reset":
                return new GenericCmd(CmdType.RESET, t);
            case "dnf":
                int DNFRacerNumber;

                if(tmpArr.length < 2)
                    return null;

                try {
                    DNFRacerNumber = Integer.parseInt(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new DNFCommand(t, DNFRacerNumber);
            case "cancel":
                int racer;

                    if(tmpArr.length < 2)
                        return null;

                    try {
                        racer = Integer.parseInt(tmpArr[1]);
                    }catch (Exception e){
                        return null;
                }

                return new CancelCmd(t, racer);
            case "trig":
                if(tmpArr.length < 2)
                    return null;

                try {
                    channel = Integer.parseInt(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new TriggerCmd(t, channel);
            case "start":
                return new GenericCmd(CmdType.START, t);
            case "finish":
                return new GenericCmd(CmdType.FINISH, t);
            case "time":
                if(tmpArr.length < 2)
                    return null;

                LocalTime toSet;
                try {
                    toSet = LocalTime.parse(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new TimeCmd(t, toSet);
            case "event":
                RaceType raceType;

                if(tmpArr.length < 2)
                    return null;

                switch(tmpArr[1].toUpperCase()){
                    case "IND":
                        raceType = RaceType.IND;
                        break;
                    case "PARIND":
                        raceType = RaceType.PARIND;
                        break;
                    case "GRP":
                        raceType = RaceType.GRP;
                        break;
                    case "PARGRP":
                        raceType = RaceType.PARGRP;
                        break;
                    default:
                        return null;
                }

                return new EventCmd(t, raceType);
            case "newrun":
                return new GenericCmd(CmdType.NEWRUN, t);
            case "endrun":
                return new GenericCmd(CmdType.ENDRUN, t);
            case "num":
                if(tmpArr.length < 2)
                    return null;

                try {
                    racer = Integer.parseInt(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new NumCmd(t, racer);
            case "disc":
                if(tmpArr.length < 2)
                    return null;

                try {
                    channel = Integer.parseInt(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new DisconnectCmd(t, channel);
            case "conn":
                SensorType sensorType;

                if(tmpArr.length < 3)
                    return null;

                switch(tmpArr[1].toUpperCase()){
                    case "EYE":
                        sensorType = SensorType.EYE;
                        break;
                    case "GATE":
                        sensorType = SensorType.GATE;
                        break;
                    case "PAD":
                        sensorType = SensorType.PAD;
                        break;
                    default:
                        return null;
                }

                try {
                    channel = Integer.parseInt(tmpArr[2]);
                }catch (Exception e){
                    return null;
                }

                return new ConnectCmd(sensorType, channel,t);
            case "print":
                int raceNum;

                if(tmpArr.length < 2)
                    return new PrintCmd(t);

                try {
                    raceNum = Integer.parseInt(tmpArr[1]);
                }catch (Exception e){
                    return null;
                }

                return new PrintCmd(t, raceNum);
            case "clr":
                //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                return null;
            case "swap":
                //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                return null;
            case "export":
                //TODO IN THE FUTURE NOT NEEDED IN SPRINT 1
                return null;
            default:
                return null;
        }

    }

    public abstract String ToString();
}
