package com.haxorz.lab4;

import java.math.BigDecimal;
import java.util.HashMap;

public class ATM {

    private Bank bank;
    private long curAcctNum;
    private int curPIN;
    private Account curAcct;

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

}
