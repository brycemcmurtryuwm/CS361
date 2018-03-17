package com.haxorz.ChronoTimer.Races;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RunRepository {

    public static final HashMap<Integer, String> CompletedRuns = new HashMap<>();

    public static final HashMap<Integer, List<Athlete>> AthletesPerRun = new HashMap<>();

    private static String currentRun = "";

    public static void addToCurrentRun(String s){
        currentRun += s;
    }

    public static void EndCurrentRun(int raceNumber){
        CompletedRuns.put(raceNumber, currentRun);
        currentRun = "";
    }

    public static String getCurrentRun() {
        return currentRun;
    }

    public static void clear() {
        currentRun = "";
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

    public static List<AthleteJson> getAthletsStatus(int raceNumber){
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

}
