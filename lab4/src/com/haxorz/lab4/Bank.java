package com.haxorz.lab4;

import java.util.HashMap;

public class Bank {

    private final HashMap<String, Account> accts;
    
	public Bank(HashMap<String, Account> accts){

        this.accts = accts;
    }

	//validate(account)
	public boolean validate(Account temp) {
		return acctExists(temp);
	}

	public boolean acctExists(Account temp){
		return accts.containsKey(Long.toString(temp.getAccountNumber()));
    }
}
