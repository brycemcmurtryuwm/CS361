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

	public BigDecimal withdraw(BigDecimal amount){
		if(amount.compareTo(BigDecimal.valueOf(0)) == -1) throw new IllegalArgumentException("The amount to withdraw cannot be less than 0");
		if(amount.compareTo(balance) <= 0){
			balance = balance.subtract(amount);
			return amount;
		}
		throw new IllegalArgumentException("The amount to withdraw cannot be greater than your current balance");
	}
	
	public long getAccountNumber() {
		return accountNumber;
	}
}
