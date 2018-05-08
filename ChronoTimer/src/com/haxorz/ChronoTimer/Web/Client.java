package com.haxorz.ChronoTimer.Web;

import com.google.gson.Gson;
import com.haxorz.ChronoTimer.Races.Race;
import com.haxorz.ChronoTimer.Races.RunRepository;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Client implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        try {
            // Client will connect to this location
            URL site = new URL("http://localhost:8000/sendresults");
            HttpURLConnection conn = (HttpURLConnection) site.openConnection();

            // now create a POST request
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            Gson gson = new Gson();
            String json = gson.toJson(RunRepository.getAthleteStatus(Race.RunNumber));

            // write out string to output buffer for message
            out.writeBytes(json);
            out.flush();
            out.close();

            System.out.println("Done sent to server");

            InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

            // string to hold the result of reading in the response
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up
            // the Response
            int nextChar;
            while ((nextChar = inputStr.read()) > -1) {
                sb = sb.append((char) nextChar);
            }
            System.out.println("Return String: " + sb);

        } catch (Exception e) {
            System.out.println("Could not connect to Server");
        }
    }

}
