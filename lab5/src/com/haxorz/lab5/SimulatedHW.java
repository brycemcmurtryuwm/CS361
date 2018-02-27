package com.haxorz.lab5;

import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class SimulatedHW {
    private CardReader reader = new CardReader();
    private CashDispenser dispenser = new CashDispenser();
    private Printer printer = new Printer();
    private PrintStream display;

    private CardNumberListener cardNumberListener = null;

    public void connectATM(ATM atm){
        cardNumberListener = atm;
    }

    public SimulatedHW(PrintStream display){

        this.display = display;
    }

    public void execute(String cmd){
        String[] tmpArr = cmd.split(" ");
        if(tmpArr.length < 2) return;

        boolean firstArgTime = true;

        //this tells us if it is a legal time
        //according to ISO local time, which
        //it appeared to be in the PDF
        try{
            LocalTime.parse(tmpArr[0]);
        } catch(DateTimeParseException e){
            firstArgTime = false;
        }

        //this reduces the size of the array since we don't have to deal with the time anymore
        tmpArr = firstArgTime ? Arrays.copyOfRange(tmpArr, 1,2) : tmpArr;

        //this switch takes all the commands possible for the hw to read
        switch (tmpArr[0]){
            case "CARDREAD":
                try{
                    long input = Long.parseLong(tmpArr[1]);
                    if(cardNumberListener != null)
                        cardNumberListener.acctNumberAvailable(input);
                }
                catch(NumberFormatException e){
                    display.println("Invalid card number");
                    break;
                }
                break;

            case "DIS":
                display.println(tmpArr[1]);
            break;
            case "PRINT":
                printer.print(tmpArr[1]);
            break;
            case "DISPENSE":
                try {
                    int amount = Integer.parseInt(tmpArr[1]);
                    dispenser.dispense(amount);
                } catch(NumberFormatException e) {break;}
                break;
            default: break;

        }
    }


}
