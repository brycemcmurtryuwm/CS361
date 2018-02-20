package com.haxorz.lab4;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class driver {
	public static void main(String[] args){
		startDriver(System.in, System.out);
	}
	
	public static void startDriver(InputStream in, PrintStream out){
		ATM atm = new ATM();
		
		outerloop:
		while(true){
			Scanner sc = new Scanner(in);
			atm.start();
			long acctNum;

			out.println("Welcome to the ATM.");
			while(true){
				out.println("Please enter your card number : ");
				String tmp = sc.next();
				try{
					acctNum = Long.parseLong(tmp);
					break;
				}catch(NumberFormatException e){
					out.println("That account number wasn't valid.");
					continue;
				}
			}
			if(!atm.EnterAcctNum(acctNum)){
				out.println("No account found!");
				continue;
			}
			int attempts = 0;
			int pin;
			while(true){
				out.println("Enter PIN : ");
				String tmp = sc.next();
				try{
					pin = Integer.parseInt(tmp);
				}catch(NumberFormatException e){
					out.println("That PIN wasn't valid. Try again : ");
					continue;
				}
				if(!atm.EnterPIN(pin) && attempts > 5){
					out.println("Too many wrong pin attempts.");
					continue outerloop;
				}
				else if(!atm.EnterPIN(pin)){
					++attempts;
					out.println("Wrong PIN.");
					continue;
				}
				break;
			}

			selectType:
			while(true){
				out.println("Enter balance, deposit or withdrawl :");
				String tmp = sc.next();
				switch(tmp.toLowerCase()){
					case "balance":
					case "b":
						balance(atm, out);
						if (!makeAnotherTrans(sc, out))
							break selectType;
						break;
					case "d":
					case "deposit" :
						deposit(sc, out, atm);
						balance(atm, out);
						if (!makeAnotherTrans(sc, out))
							break selectType;
						break;
					case "w":
					case "withdrawl" :
						withdrawl(sc, out, atm);
						balance(atm, out);
						if (!makeAnotherTrans(sc, out))
							break selectType;
						break;
					default :
						out.println("That isn't a valid transaction type.");
						break;
				}
			}
			if(exit(sc, out))
				return;
		}
	}

	private static boolean exit(Scanner sc, PrintStream out) {
		while(true){
			out.println("Would you like to exit the atm (y/n):");
			String tmp = sc.next().toLowerCase();
			if("y".equals(tmp)){
				return true;
			}
			else if("n".equals(tmp)){
				return false;
			}
		}
	}

	private static void balance(ATM atm, PrintStream out) {
		try {
			BigDecimal balance = atm.getBalance();

			out.println("Current Balance: $" + balance);
		} catch (Exception e) {
			out.println("There was an error in retrieving your account information.");
			out.println(e.getMessage());
		}
	}

	private static boolean makeAnotherTrans(Scanner sc, PrintStream out) {
		while(true){
			out.println("Would you like to make another transaction (y/n):");
			String tmp = sc.next().toLowerCase();
			if("y".equals(tmp)){
					return true;
			}
			else if("n".equals(tmp)){
				return false;
			}
		}
	}

	private static BigDecimal askForAmount(Scanner sc, PrintStream out){
		double amount;
		while(true){
			out.println("Enter the amount :");
			String tmp = sc.next();
			try{
				amount = Double.parseDouble(tmp);
				return BigDecimal.valueOf(amount);
			}catch(NumberFormatException e){
				out.println("That isn't a valid amount.");
			}
		}
	}

	private static boolean deposit(Scanner sc, PrintStream out, ATM atm){
		BigDecimal amount = askForAmount(sc, out);
		try {
			atm.deposit(amount);
			out.println("Deposited $" + amount + " to account.");
			return true;
		}
		catch(Exception e){
			out.println("Deposit was not successful");
			out.println(e.getMessage());
			return false;
		}
	}

	private static boolean withdrawl(Scanner sc, PrintStream out, ATM atm){
		BigDecimal amount = askForAmount(sc, out);
		try {
			atm.withdraw(amount);
			out.println("Withdrew $" + amount + " from account.");
			return true;
		}
		catch(Exception e){
			out.println("Withdraw was not successful");
			out.println(e.getMessage());
			return false;
		}
	}
}
