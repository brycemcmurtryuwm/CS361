package com.haxorz.ChronoTimer.Hardware;

import com.google.gson.Gson;
import com.haxorz.ChronoTimer.Races.RunRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Export {

    /**
     * Exports the run with the given run number to a file
     * called "RUN<number>.txt"
     *
     * @param runNum the run number that we wish to export
     */
    public static void SaveRunToFile(int runNum){
        Gson gson = new Gson();
        String json = gson.toJson(RunRepository.getAthleteStatus(runNum));

        File file = new File("RUN" + runNum + ".txt");
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            writer.write(json);
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
