package com.haxorz.lab7;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Client {

    public static void sendEmployeesToDir(List<Employee> employees) {
        if(employees == null) return;

        try {
            System.out.println("in the client");

            // Client will connect to this location
            URL site = new URL("http://localhost:8000/sendresults");
            HttpURLConnection conn = (HttpURLConnection) site.openConnection();

            // now create a POST request
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            // build a string that contains JSON from console
            String content = "ADD ";

            Gson g = new Gson();
            content += g.toJson(employees);

            // write out string to output buffer for message
            out.writeBytes(content);
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
            e.printStackTrace();
        }
    }

    public static void sendEmployeesToDir(Employee employee) {
        if(employee == null) return;

        sendEmployeesToDir(Arrays.asList(employee));
    }

    public static void sendCmd(DirectoryCmdType cmdType) {
        try {
            System.out.println("in the client");

            // Client will connect to this location
            URL site = new URL("http://localhost:8000/sendresults");
            HttpURLConnection conn = (HttpURLConnection) site.openConnection();

            // now create a POST request
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            // build a string that contains JSON from console
            String content = cmdType.toString();

            // write out string to output buffer for message
            out.writeBytes(content);
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
            e.printStackTrace();
        }
    }
}
