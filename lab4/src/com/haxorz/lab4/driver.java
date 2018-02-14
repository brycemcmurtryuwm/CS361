package com.haxorz.lab4;
import java.util.Scanner;

public class driver {
	public static void main(String[] args){
		ATM atm = new ATM();

		outerloop:
		while(true){
			Scanner sc = new Scanner(System.in);
			long acctNum;

			System.out.println("Welcome to the ATM.");
			while(true){
				System.out.println("Please enter your card number : ");
				String tmp = sc.next();
				try{
					acctNum = Long.parseLong(tmp);
					break;
				}catch(NumberFormatException e){
					System.out.println("That account number wasn't valid.");
					continue;
				}
			}
			if(!atm.EnterAcctNum(acctNum)){
				System.out.println("No account found!");
				continue;
			}
			int attempts = 0;
			int pin;
			while(true){
				System.out.println("Enter PIN : ");
				String tmp = sc.next();
				try{
					pin = Integer.parseInt(tmp);
				}catch(NumberFormatException e){
					System.out.println("That PIN wasn't valid. Try again : ");
					continue;
				}
				if(!atm.EnterPIN(pin) && attempts > 5){
					System.out.println("Too many wrong pin attempts.");
					continue outerloop;
				}
				else if(!atm.EnterPIN(pin)){
					++attempts;
					System.out.println("Wrong PIN.");
					continue;
				}
				break;
			}
			int transactionType;
			selectType:
			while(true){
				System.out.println("Enter deposit or withdrawl :");
				String tmp = sc.next();
				switch(tmp.toLowerCase()){
					case "deposit" :
						transactionType = 0;
						break selectType;
					case "withdrawl" :
						transactionType = 1;
						break selectType;
					default :
						break;
				}
				System.out.println("That isn't a valid transaction type.");
			}
			switch(transactionType){
				case 0:
					deposit(sc, atm);
					break;
				case 1:
					withdrawl(sc, atm);
					break;
			}
		}
	}
	public static double askForAmount(Scanner sc){
		double amount;
		while(true){
			System.out.println("Enter the amount :");
			String tmp = sc.next();
			try{
				amount = Double.parseDouble(tmp);
			}catch(NumberFormatException e){}
			System.out.println("That isn't a valid amount.");
		}
	}
	public static boolean deposit(Scanner sc, ATM atm){
		double amount = askForAmount(sc);
		try {
			atm.deposit(amount);
			System.out.println("Deposited $" + amount + " to account.");
			return true;
		}
		catch(Exception e){
			System.out.println("Deposit was not successful");
			return false;
		}
	}
	public static boolean withdrawl(Scanner sc, ATM atm){
		double amount = askForAmount(sc);
		try {
			atm.withdraw(amount);
			System.out.println("Withdrew $" + amount + " from account.");
			return true;
		}
		catch(Exception e){
			System.out.println("Withdrawl was not successful");
			return false;
		}
	}
}
