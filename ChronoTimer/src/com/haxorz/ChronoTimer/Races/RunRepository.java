package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.SystemClock;

import java.security.PublicKey;
import java.time.LocalTime;
import java.util.*;

public class RunRepository extends Observable implements Observer {

    public static final HashMap<Integer, String> CompletedRuns = new HashMap<>();

    public static final HashMap<Integer, List<Athlete>> AthletesPerRun = new HashMap<>();

    private static String currentRun = "";

    public static List<String> Finalists = new ArrayList<>();
    public static List<Athlete> Running = new ArrayList<>();
    public static List<String> InQueue = new ArrayList<>();
    public static RaceType RaceType = com.haxorz.ChronoTimer.Races.RaceType.IND;
    public static LocalTime GRPSTART = null;
    public static Object Lock = new Object();

    private static RunRepository _repository = new RunRepository();

    public static RunRepository getRunRepository(){ return _repository;}

    private RunRepository(){}

    public static void addToCurrentRun(String s){
        currentRun += s;
    }

    public static void EndCurrentRun(int raceNumber){
        CompletedRuns.put(raceNumber, currentRun);
        clearCurrentRun();
    }

    public static String getCurrentRun() {
        return currentRun;
    }

    public static void clear() {
        clearCurrentRun();
        CompletedRuns.clear();
        AthletesPerRun.clear();
    }

    public static void addAthlete(int raceNumber, Athlete athlete){
        if(AthletesPerRun.containsKey(raceNumber)){
            List<Athlete> athletes = AthletesPerRun.get(raceNumber);

            if(!athletes.contains(athlete)){
                athletes.add(athlete);
            }

        }
        else {
            List<Athlete> athletes = new ArrayList<>();
            athletes.add(athlete);
            AthletesPerRun.put(raceNumber, athletes);
        }
    }


    public static void removeAthlete(int raceNumber, Athlete athlete) {
        if(AthletesPerRun.containsKey(raceNumber)){
            List<Athlete> athletes = AthletesPerRun.get(raceNumber);

            athletes.remove(athlete);
        }
    }

    public static List<AthleteJson> getAthleteStatus(int raceNumber){
        if(!AthletesPerRun.containsKey(raceNumber))
            return new ArrayList<>();

        List<Athlete> athletes = AthletesPerRun.get(raceNumber);
        List<AthleteJson> toReturn = new ArrayList<>();

        for (int i = 0; i < athletes.size(); i++) {
            Athlete athlete = athletes.get(i);
            RaceTime timeTracker = athlete.getTimeTracker(raceNumber);

            if(timeTracker == null)
                continue;

            AthleteJson obj = new AthleteJson();

            obj.AthleteNumber = athlete.getNumber();
            obj.Status = timeTracker.toString();

            toReturn.add(obj);
        }

        return toReturn;
    }

    public static void clearCurrentRun() {
        currentRun = "";
        synchronized (Lock){
            Finalists.clear();
            Running.clear();
            InQueue.clear();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Race){
            synchronized (Lock){
                RaceType = ((Race) o).getRaceType();
                Finalists = ((Race) o).getCompletedTimes();
                Running = ((Race) o).athletesRunning();
                InQueue = ((Race) o).getAthletesInQueue();

                if(RaceType == com.haxorz.ChronoTimer.Races.RaceType.GRP)
                    GRPSTART = ((GrpRace)o).getStartTime();

                _repository.setChanged();
                _repository.notifyObservers();
            }

        }
    }
}
