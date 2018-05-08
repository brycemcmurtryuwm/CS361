package com.haxorz.ChronoTimer.Races;

import java.util.HashMap;

public class Athlete {

    private HashMap<Integer, RaceTime> timesTracked = new HashMap<>();

    private int _number;

    public Athlete(int number) {
        this._number = number;
    }

    public RaceTime getTimeTracker(int raceNumber){
        RaceTime timeTracker = timesTracked.get(raceNumber);

        return timeTracker;
    }

    public void discardRun(int raceNumber){

        timesTracked.remove(raceNumber);
        RunRepository.removeAthlete(raceNumber, this);
    }

    public void registerForRace(int raceNumber){
        timesTracked.put(raceNumber, new RaceTime(this));
        RunRepository.addAthlete(raceNumber, this);
    }

    public boolean registeredForRace(int raceNumber){
        return timesTracked.containsKey(raceNumber);
    }

    public int getNumber(){
        return _number;
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Athlete athlete = (Athlete) o;

        return _number == athlete._number;
    }
}
