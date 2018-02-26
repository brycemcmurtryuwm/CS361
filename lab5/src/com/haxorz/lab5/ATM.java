package com.haxorz.lab5;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

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
    private boolean readCommand(Scanner sc, PrintStream out){
        String tmp = sc.nextLine();
        String[] tmpArr = tmp.split(" ");
        if(tmpArr.length != 2) return false;
        switch (tmpArr[0]){
            case "CARDREAD":
                if(state != 0) return false;
                try{
                    long input = Long.parseLong(tmpArr[1]);
                    curAcctNum = input;
                    return bank.acctExists(curAcctNum);
                }
                catch(NumberFormatException e){
                    return false;
                }
            case "NUM":
                switch(state) {
                    case(1):
                        try {
                            curPIN = Integer.parseInt(tmpArr[1]);
                            if(!bank.validate(curAcctNum, curPIN)) return false;
                            curAcct = bank.getAcct(curAcctNum, curPIN);
                            return true;
                        } catch(NumberFormatException e) {
                            return false;
                        }
                    case(3):
                        try {
                            double tmp1 = Double.parseDouble(tmpArr[1]);
                            BigDecimal amount = BigDecimal.valueOf(tmp1);

                            //this line may need to be changed to take into account when only some money can be withdrawn
                            curAcct.withdraw(amount);
                            return true;
                        } catch(NumberFormatException e) {
                            return false;
                        } catch(IllegalArgumentException e){
                            return false;
                        }
                    default: return false;
                }
            case "BUTTON":
                switch(tmpArr[1]){
                    case "CANCEL":
                        curAcct = null;
                        curAcctNum = 0;
                        curPIN = 0;
                        System.out.println("EJECT CARD");
                        break;
                    case "W":
                        if(state == 2){
                            System.out.println("Select te amout you would like to withdraw :");
                            state = 3;
                            return true;
                        }
                        else return false;
                    case "CB":
                        if(state == 2){
                            System.out.println("Current balance : $" + curAcct.getBalance());
                            return true;
                        }
                        else return false;
                    default: return false;
                }
                break;
            case "DIS":
                System.out.println(tmpArr[1]);
                return true;
            case "PRINT":
                printer.print(tmpArr[1],true);  //todo: timestamp reading
                return true;
            default: return false;

        }
        return false;
    }
}
