package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Hardware.ChannelListener;
import com.haxorz.ChronoTimer.Hardware.HWEventType;
import com.haxorz.ChronoTimer.Hardware.HardwareEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public abstract class Race extends Observable implements ChannelListener {

    public static final HashMap<Integer,Athlete> COMPETITORS = new HashMap<>();

    public static int RunNumber = 0;

    public abstract RaceType getRaceType();

    public abstract void executeCmd(CTCommand cmd);

    public List<String> getCompletedTimes(){
        List<String> times = new ArrayList<>();

        List<Athlete> athletes = getCompletedAthletes();

        for(Athlete a : athletes){
            times.add("Athlete " + a.getNumber() + ": " + a.getTimeTracker(Race.RunNumber).toStringMinutes());
        }

        return times;
    }

    public List<String> getAthletesRunning(){
        List<String> times = new ArrayList<>();

        List<Athlete> athletes = athletesRunning();

        for(Athlete a : athletes){
            times.add("Athlete " + a.getNumber());
        }

        return times;
    }

    public List<String> getAthletesInQueue(){
        List<String> times = new ArrayList<>();

        List<Athlete> athletes = athletesInQueue();

        for(Athlete a : athletes){
            times.add("Athlete " + a.getNumber());
        }

        return times;
    }

    protected abstract List<Athlete> getCompletedAthletes();

    protected abstract List<Athlete> athletesRunning();

    protected abstract List<Athlete> athletesInQueue();

}
