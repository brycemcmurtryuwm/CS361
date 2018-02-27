package com.haxorz.lab5;

import java.math.BigDecimal;

public class Account {

	private long accountNumber;
	private int pin;
	private BigDecimal balance;


	public Account(long accountNumber, int pin, BigDecimal balance){
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
	}
	//validate (correct PIN?)
	public boolean validate(long accountNumber, int pin){
		return this.pin == pin && this.accountNumber == accountNumber;
	}
	public BigDecimal getBalance(){
		return balance;
	}

	public void deposit(BigDecimal amount){
		if(amount.compareTo(BigDecimal.valueOf(0)) == -1) throw new IllegalArgumentException("The amount to deposit cannot be less than 0");

		balance = balance.add(amount);
	} //throw illegal

	/**
	 * @param amount	requested amount to be withdrawn
	 * @return 			actual amount taken out
	 */
	public BigDecimal withdraw(BigDecimal amount){
		if(amount.compareTo(BigDecimal.ZERO) == -1) throw new IllegalArgumentException("The amount to withdraw cannot be less than 0");
		if(amount.compareTo(balance) <= 0) {balance = balance.subtract(amount);}

		//if we dont have enough money we withdraw the whole account, as shown in expected output
		else{
			amount = balance;
			balance = BigDecimal.ZERO;
		}
		return amount;
	}
	
	public long getAccountNumber() {
		return accountNumber;
	}
}
