package com.haxorz.ChronoTimer.Races;

import java.util.HashMap;

public class Athlete {

    private HashMap<Integer, RaceTime> timesTracked = new HashMap<>();

    private int _number;

    public Athlete(int number) {
        this._number = number;
    }

    public RaceTime getTimeTracker(int raceNumber){ //lazy initialization
        RaceTime timeTracker = timesTracked.get(raceNumber);

        return timeTracker;
    }

    public void registerForRace(int raceNumber){
        timesTracked.put(raceNumber, new RaceTime(this));
    }

    public int getNumber(){
        return _number;
    }




}
