package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.CTCommand;

import java.io.*;
import java.util.Scanner;

/**
 * A console interface for our chronoTimer
 */
public class Simulator {
    public static boolean EXIT = false;


    /**
     * Reads input from the terminal or a file
     *
     * Continually asks for input until asked to quit
     *
     * @param args not used
     */
    public static void main(String[] args){
        while (true){
            System.out.println("To read from a file press '1' to use the console press '2'");

            Scanner sc = new Scanner(System.in);
            String tmp = sc.nextLine();
            try{
                int choice = Integer.parseInt(tmp);
                if(choice == 1){
                    File f;
                    String path;

                    do{
                        System.out.println("Enter File Name/Path: ");
                        path = sc.nextLine();
                        path.trim();
                        f = new File(path);
                    }while (f == null || !f.exists());

                    ReadFromFile(System.out, path);
                }
                else if(choice == 2){
                    ReadConsoleInput(System.in, System.out);
                }
                else {
                    continue;
                }
                break;
            }catch(Exception e){
                continue;
            }
        }

    }

    /**
     * @param out where the output shall be put
     * @param filePath path to the fie containing
     *                 the commands to be executed
     */
    public static void ReadFromFile(PrintStream out, String filePath){

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

            String line;
            ChronoTimer chronoTimer = new ChronoTimer(out);

            while((line = bufferedReader.readLine()) != null && !EXIT) {
                CTCommand cmd = CTCommand.ParseFromString(line);
                if(cmd != null)
                    chronoTimer.executeCmd(cmd);
            }

            bufferedReader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read input from the console
     *
     * @param in the input stream we read input from
     * @param out wher to put the output from the timer
     */
    public static void ReadConsoleInput(InputStream in, PrintStream out){
        ChronoTimer chronoTimer = new ChronoTimer(out);
        Scanner sc = new Scanner(in);

        while(!EXIT){
            String line = sc.nextLine();

            CTCommand cmd = CTCommand.ParseFromString(line);
            if(cmd != null)
                chronoTimer.executeCmd(cmd);
        }
    }

}
