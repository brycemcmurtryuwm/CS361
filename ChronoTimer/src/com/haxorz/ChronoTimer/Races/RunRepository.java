package com.haxorz.ChronoTimer.Races;

import javafx.util.Pair;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

/**
 * a class used to keep track of all the athletes who have completed a run
 */
public class RunRepository extends Observable implements Observer {

    public static final HashMap<Integer, String> CompletedRuns = new HashMap<>();

    public static final HashMap<Integer, List<Athlete>> AthletesPerRun = new HashMap<>();

    private static String currentRun = "";

    public static List<String> Finalists = new ArrayList<>();
    public static List<Athlete> Running = new ArrayList<>();
    public static List<String> InQueue = new ArrayList<>();
    public static RaceType RaceType = com.haxorz.ChronoTimer.Races.RaceType.IND;
    public static LocalTime GRPSTART = null;
    public static final Object Lock = new Object();

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

    /**
     * Clears all the runs ran
     *
     * Useful upon a system reset
     */
    public static void clear() {
        clearCurrentRun();
        CompletedRuns.clear();
        AthletesPerRun.clear();
    }

    /**
     * @param raceNumber the race number they are competing in
     * @param athlete the athlete to be added
     */
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


    /**
     * @param raceNumber the race they are to be removed from
     * @param athlete the athlete to be removed
     */
    public static void removeAthlete(int raceNumber, Athlete athlete) {
        if(AthletesPerRun.containsKey(raceNumber)){
            List<Athlete> athletes = AthletesPerRun.get(raceNumber);

            athletes.remove(athlete);
        }
    }

    /**
     * @param raceNumber the raceNumber to be got
     * @return a list of AthLete json listing each athlete's
     *         current status
     */
    public static List<AthleteJson> getAthleteStatus(int raceNumber){
        if(!AthletesPerRun.containsKey(raceNumber))
            return new ArrayList<>();

        List<Athlete> athletes = AthletesPerRun.get(raceNumber);
        List<AthleteJson> toReturn = new ArrayList<>();
        List<Pair<Duration, AthleteJson>> athleteOrder = new ArrayList<>();

        for (int i = 0; i < athletes.size(); i++) {
            Athlete athlete = athletes.get(i);
            RaceTime timeTracker = athlete.getTimeTracker(raceNumber);

            if(timeTracker == null)
                continue;

            AthleteJson obj = new AthleteJson();

            obj.AthleteNumber = athlete.getNumber();
            obj.Status = timeTracker.toString();
            obj.Time = timeTracker.toStringMinutes();
            obj.TimeStamp = String.valueOf(System.nanoTime());
            obj.State = "finished";

            if(timeTracker.getStartTime() == null)
                obj.State = "waiting";

            if(!timeTracker.isDNF() && timeTracker.getStartTime() != null && timeTracker.getEndTime() == null){
                obj.State = "racing";
                obj.Time = String.valueOf(timeTracker.toMillis());
            }

            if(timeTracker.getStartTime() != null && timeTracker.getEndTime() == null){
                athleteOrder.add(new Pair<>(Duration.ofDays(99999), obj));
            }
            else if((timeTracker.getStartTime() == null && timeTracker.getEndTime() == null) || timeTracker.isDNF()){
                athleteOrder.add(new Pair<>(Duration.ofDays(9999999), obj));
            }
            else {
                athleteOrder.add(new Pair<>(timeTracker.getDuration(), obj));
            }
        }

        athleteOrder.sort(Comparator.comparing(Pair::getKey));

        int place = 1;
        for (Pair<Duration, AthleteJson> item: athleteOrder){
            item.getValue().Place = place++;
            toReturn.add(item.getValue());
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
