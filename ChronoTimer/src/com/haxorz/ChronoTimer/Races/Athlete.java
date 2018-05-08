package com.haxorz.ChronoTimer.Races;

import java.util.HashMap;


/**
 * Keeps track of the individual competitors in the events
 * NOTE: Every athlete must have a unique number
 */
public class Athlete {

    //information about the athlete
    private HashMap<Integer, RaceTime> timesTracked = new HashMap<>();
    private int _number;

    public Athlete(int number) {
        this._number = number;
    }

    /**
     * @param raceNumber the number for an event that an athlete ran
     * @return the RaceTime object detailing that run, null if not found
     */
    public RaceTime getTimeTracker(int raceNumber){
        RaceTime timeTracker = timesTracked.get(raceNumber);
        return timeTracker;
    }

    /**
     * @param raceNumber number of run that should be be removed as it is invalid etc.
     */
    public void discardRun(int raceNumber){
        timesTracked.remove(raceNumber);
        RunRepository.removeAthlete(raceNumber, this);
    }

    /**
     * @param raceNumber connects the race with the athlete, allowing them to be tracked
     */
    public void registerForRace(int raceNumber){
        timesTracked.put(raceNumber, new RaceTime(this));
        RunRepository.addAthlete(raceNumber, this);
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
