package com.haxorz.lab12;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Driver {

    public static boolean STOP = false;

    public static void main(String[] args){
        System.out.println("Initializing");

        ReadConsoleInput(System.in, System.out);

    }

    public static void ReadConsoleInput(InputStream in, PrintStream out){
        GarageDoorSystem chronoTimer = new GarageDoorSystem(out);
        Scanner sc = new Scanner(in);

        while(!STOP){
            String line = sc.nextLine();

            GarageCmd cmd = GetCommand(line);
            if(cmd != null)
                chronoTimer.execute(cmd);
        }
    }


    public static GarageCmd GetCommand(String s){

        switch (s.toLowerCase()){
            case "lclick":
                return  GarageCmd.LightClick;
            case "dclick":
                return  GarageCmd.DoorClick;
            case "limit":
                return  GarageCmd.DoorLimit;
            case "safety":
                return  GarageCmd.DoorSafety;
            case "off":
                return  GarageCmd.OFF;
            default:
                return null;
        }

    }

}
