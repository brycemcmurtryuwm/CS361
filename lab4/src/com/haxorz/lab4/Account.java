package com.haxorz.lab4;

public class Account {

	//acctNum
	//PIN Code
	//balance
	private long accountNumber;
	private int pin;
	private double balance;


	public Account(long accountNumber, int pin, double balance){
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
	}
	//validate (correct PIN?)
	public boolean validate(long accountNumber, int pin){
		if(this.accountNumber != accountNumber) return false;
		if(this.pin != pin) return false;
		return true;
	}
	public double getBalance(){
		return balance;
	}

	public void deposit(double amount){
		balance += amount;
	} //throw illegal

	public double withdraw(double amount){
		if(amount < 0) throw new IllegalArgumentException();
		if(amount <= balance){
			balance -= amount;
			return amount;
		}
		return 0;
	}
	
	public long getAccountNumber() {
		return accountNumber;
	}
}
