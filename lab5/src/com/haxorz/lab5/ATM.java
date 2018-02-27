package com.haxorz.lab5;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.time.format.*;

public class ATM implements CardNumberListener {

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

    public boolean EnterAcctNum(long acctNum){
        if (bank.acctExists(acctNum))
        {
            curAcctNum = acctNum;
            curPIN = 0;
            curAcct = null;
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



    public void execute(String cmd){//
        String[] tmpArr = cmd.split(" ");
        if(tmpArr.length < 2) return;

        LocalTime t = LocalTime.now();
        boolean firstArgTime = true;

        //this tells us if it is a legal time
        //according to ISO local time, which
        //it appeared to be in the PDF
        try{
            t = LocalTime.parse(tmpArr[0]);
        } catch(DateTimeParseException e){
            firstArgTime = false;
        }

        //this reduces the size of the array since we don't have to deal with the time anymore
        tmpArr = firstArgTime ? Arrays.copyOfRange(tmpArr, 1,2) : tmpArr;

        //this switch takes all the commands possible for the ATM to read
        switch (tmpArr[0]){
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
                            executeOnHW("DISPENSE " + amount);
                            String receipt = "PRINT " + t.format(DateTimeFormatter.ISO_LOCAL_TIME) + " W $" + amount;
                            executeOnHW(receipt);
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
                        if(state!=0)
                            executeOnHW("DIS \"EJECT CARD\"");
                        state = 0;
                        break;
                    case "W":
                        if(state == 2) state = 3;
                        break;
                    case "CB":
                        if(state == 2){
                            String balanceReceipt = t.format(DateTimeFormatter.ISO_LOCAL_TIME) + " Current Balance: $" + curAcct.getBalance();
                            executeOnHW("PRINT " + balanceReceipt);
                            executeOnHW("DIS " + balanceReceipt);
                        }
                        break;
                    default: break;
                }
                break;
            default: break;

        }

        switch(state){
            case 0:
                break;
            case 1:
                executeOnHW("DIS \"Enter PIN\"");
                break;
            case 2:
                executeOnHW("DIS \"Choose Transaction\"");
                break;
            case 3:
                executeOnHW("DIS \"Amount?\"");
                break;
        }

    }

    public void executeOnHW(String cmd){
        this.hw.execute(cmd);
    }

    @Override
    public void acctNumberAvailable(long acctNum) {
        this.EnterAcctNum(acctNum);
        if (bank.acctExists(curAcctNum))
        {
            state = 1;
            executeOnHW("DIS \"Enter PIN\"");
        }
        else state = 0;
    }
}
