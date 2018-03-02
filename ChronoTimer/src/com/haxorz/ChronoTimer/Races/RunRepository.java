package com.haxorz.ChronoTimer.Races;

import java.util.HashMap;

public class RunRepository {

    public static final HashMap<Integer, String> CompletedRuns = new HashMap<>();

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
}
