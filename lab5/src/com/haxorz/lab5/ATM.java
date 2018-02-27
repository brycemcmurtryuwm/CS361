package com.haxorz.lab5;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.time.format.*;

public class ATM {

    private Bank bank;
    private long curAcctNum;
    private int curPIN;
    private Account curAcct;

    //0-waiting for a card, 1-waiting for a pin, 2-select transaction, 3-select amount of money to deposit
    private int state = 0;

    private SimulatedHW hw;


    public ATM(SimulatedHW hw){

        this.hw = hw;
    }
    
    //start method
    public void start(){

        HashMap<String, Account> customers = new HashMap<String, Account>();
        customers.put("1234" ,new Account(1234,6789,BigDecimal.valueOf(80)));
        customers.put("1235",new Account(1235,000,BigDecimal.ZERO));
        customers.put("6789",new Account(6789,4321,BigDecimal.valueOf(60)));

        bank = new Bank(customers);
        curAcctNum = 0;
        curPIN = 0;
        curAcct = null;
    }

    public void start(Bank bank){
        this.bank = bank;
        curAcctNum = 0;
        curPIN = 0;
        curAcct = null;
    }

    public void execute(String cmd){//
        //Button w/cb(balance)/cancel/d
        //num

    }

    public void executeOnHW(String cmd){
        this.hw.execute(cmd);
    }

    public boolean EnterAcctNum(long acctNum){
        if (bank.acctExists(acctNum))
        {
            curAcctNum = acctNum;
            return true;
        }

        return false;
    }

    public boolean EnterPIN(int pin){
        if(bank.validate(curAcctNum, pin)){
            curPIN = pin;
            curAcct = bank.getAcct(curAcctNum, curPIN);
            return true;
        }

        return false;
    }

    public BigDecimal withdraw(BigDecimal amount) throws Exception { //throw illegal
        if(curAcct == null){
            throw new Exception("Account could not be validated or does not exist.");
        }

        return curAcct.withdraw(amount);
    }


    public void deposit(BigDecimal amount) throws Exception {
        if(curAcct == null){
            throw new Exception("Account could not be validated or does not exist.");
        }

        curAcct.deposit(amount);
    }


    public BigDecimal getBalance() throws Exception {
        if(curAcct == null){
            throw new Exception("Account could not be validated or does not exist.");
        }

        return curAcct.getBalance();
    }
    /**
     * Reads in commands with date stamps which get read as commands
     *
     * @param   sc scanner either from a file or user input
     * @param   out A printstream to a doc and/or the screen
     * @return  boolean of whether the ATM should continue operation
     */

    public boolean readCommand(Scanner sc, PrintStream out){
        String tmp = sc.nextLine();
        String[] tmpArr = tmp.split(" ");
        if(tmpArr.length != 3) return false;

        LocalTime t;

        //this tells us if it is a legal time
        //according to ISO local time, which
        //it appeared to be in the PDF
        try{
            t = LocalTime.parse(tmpArr[0]);
        } catch(DateTimeParseException e){
            return false;
        }

        //this reduces the size of the array since we don't have to deal with the time anymore
        tmpArr = Arrays.copyOfRange(tmpArr, 1,2);

        //this switch takes all the commands possible for the ATM to read
        switch (tmpArr[0]){
            case "CARDREAD":
                if(state != 0) break;
                try{
                    long input = Long.parseLong(tmpArr[1]);
                    curAcctNum = input;
                    state = bank.acctExists(curAcctNum)?1:0;
                }
                catch(NumberFormatException e){
                    out.println("Invalid card number");
                    break;
                }
                break;

            case "NUM":
                switch(state) {
                    case(1):
                        try {
                            curPIN = Integer.parseInt(tmpArr[1]);
                            if(!bank.validate(curAcctNum, curPIN)) break;
                            curAcct = bank.getAcct(curAcctNum, curPIN);
                            state = 2;

                        } catch(NumberFormatException e) {break;}
                    case(3):
                        try {
                            double tmp1 = Double.parseDouble(tmpArr[1]);
                            BigDecimal amount = BigDecimal.valueOf(tmp1);

                            //amount is now the real amount taken from the account
                            amount = curAcct.withdraw(amount);
                            cashDispenser.dispense(amount);
                            String receipt = "PRINT" + t.format(DateTimeFormatter.ISO_LOCAL_TIME) + "W $" + amount;
                            readCommand(new Scanner(receipt), out );
                            break;
                        } catch(IllegalArgumentException e) {break;}

                    default: break;
                }
                break;
            case "BUTTON":
                switch(tmpArr[1]){
                    case "CANCEL":
                        curAcct = null;
                        curAcctNum = 0;
                        curPIN = 0;
                        if(state!=0) out.println("EJECT CARD");
                        state = 0;
                        break;
                    case "W":
                        if(state == 2) state = 3;
                        break;
                    case "CB":
                        if(state == 2){
                            String balanceReceipt = "PRINT" + t.format(DateTimeFormatter.ISO_LOCAL_TIME) + "W $" + curAcct.getBalance();
                            System.out.println("Current balance : $" + curAcct.getBalance());
                        }
                        break;
                    default: break;
                }
                break;
            case "DIS":
                out.println(tmpArr[1]);
                break;
            case "PRINT":
                printer.print(tmpArr[1]);
                break;
            default: break;

        }

        //This displays the output to prompt for the next command
        switch(state){
            case 0:
                break;
            case 1:
                out.println("Enter PIN");
                break;
            case 2:
                out.println("Choose Transaction");
                break;
            case 3:
                out.println("Amount?");
                break;
        }
        return false;
    }
}
