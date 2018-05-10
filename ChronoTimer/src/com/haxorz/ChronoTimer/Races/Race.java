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

/**
 * abstract Race type, providing fuctions and fields that all types of races
 * will have including race number and competitors hashMap
 */
public abstract class Race extends Observable implements ChannelListener {

    public static final HashMap<Integer,Athlete> COMPETITORS = new HashMap<>();
    public static int RunNumber = 0;

    //abstract methods to be implemented by sub classes
    public abstract RaceType getRaceType();
    public abstract void executeCmd(CTCommand cmd);
    protected abstract List<Athlete> getCompletedAthletes();
    public abstract List<Athlete> athletesRunning();
    protected abstract List<Athlete> athletesInQueue();

    /**
     * @return a list of strings showing all the athletes who have finished racing
     */
    public List<String> getCompletedTimes(){
        List<String> times = new ArrayList<>();
        List<Athlete> athletes = getCompletedAthletes();
        for(Athlete a : athletes){
            times.add("Athlete " + a.getNumber() + ": " + a.getTimeTracker(Race.RunNumber).toStringMinutes());
        }
        return times;
    }

    /**
     * @return a list of strings showing all the athletes who are currently racing
     */
    public List<String> getAthletesRunning(){
        List<String> times = new ArrayList<>();

        List<Athlete> athletes = athletesRunning();

        for(Athlete a : athletes){
            times.add("Athlete " + a.getNumber());
        }

        return times;
    }

    /**
     * @return a list of strings showing all the athletes who are waiting to race
     */
    public List<String> getAthletesInQueue(){
        List<String> times = new ArrayList<>();

        List<Athlete> athletes = athletesInQueue();

        for(Athlete a : athletes){
            times.add("Athlete " + a.getNumber());
        }

        return times;
    }



}
