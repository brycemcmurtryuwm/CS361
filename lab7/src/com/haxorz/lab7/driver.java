package com.haxorz.lab7;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class driver {

    public static void main(String[] args){
        System.out.println("Directory Initializing");

        ReadConsoleInput(System.in, System.out);

    }

    public static void ReadConsoleInput(InputStream in, PrintStream out){
        DirectoryEditor chronoTimer = new DirectoryEditor(out);
        Scanner sc = new Scanner(in);

        while(true){
            String line = sc.nextLine();

            DirectoryCmd cmd = DirectoryCmd.ParseFromString(line);
            if(cmd != null)
                chronoTimer.executeCmd(cmd);
        }
    }
}
