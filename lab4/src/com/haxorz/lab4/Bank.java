package com.haxorz.lab4;

import java.util.HashMap;

public class Bank {

    private final HashMap<String, Account> accts;
    
	public Bank(HashMap<String, Account> accts){

        this.accts = accts;
    }

	public boolean validate(long acctNum, int pin) {
		Account account = getAcct(acctNum, pin);

		return account != null;
	}

	public Account getAcct(long acctNum, int pin){
	    if(!acctExists(acctNum))
	        return null;

	    Account account = accts.get(Long.toString(acctNum));

	    if(!account.validate(acctNum, pin))
	        return null;

	    return account;
    }

	public boolean acctExists(long acctNum){
		return accts.containsKey(Long.toString(acctNum));
    }
}
