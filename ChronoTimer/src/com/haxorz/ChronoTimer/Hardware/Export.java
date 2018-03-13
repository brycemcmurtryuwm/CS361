package com.haxorz.ChronoTimer.Hardware;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Export {

    public static void SaveRunToFile(String run, int runNum){
        Gson gson = new Gson();
        String json = gson.toJson(run);

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
