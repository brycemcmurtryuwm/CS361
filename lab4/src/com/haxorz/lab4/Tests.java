package com.haxorz.lab4;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class Tests {
    private Customer customer80;
    private Customer customer0;
    private Customer customer60;
    private Customer noExistCust;
    private ATM atm;


    //tests
    //console input
    //units tests

    @Before
    public void setup(){
        customer80 = new Customer(new Card(1234), 6789);
        customer0 = new Customer(new Card(1235), 0000);
        customer60 = new Customer(new Card(6789), 4321);
        noExistCust = new Customer(new Card(12390), 0000);

        atm = new ATM();
        atm.start();
    }

    //region Withdraw Tests
    @Test
    public void withdrawNoMoney() {
        atm.EnterAcctNum(customer0.card.AcctNum());
        atm.EnterPIN(customer0.PIN);
        try {
            atm.withdraw(BigDecimal.ZERO);
            assertEquals(BigDecimal.ZERO,atm.getBalance());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void withdrawNegativeNumber() {
        atm.EnterAcctNum(customer0.card.AcctNum());
        atm.EnterPIN(customer0.PIN);
        try {
            atm.withdraw(BigDecimal.valueOf(-10));
            fail("Withdrew a negative amount");
        } catch (Exception e) {
            assertEquals(1,1);
        }

    }

    @Test
    public void withdrawMoney() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        atm.EnterPIN(customer80.PIN);
        try {
            atm.withdraw(BigDecimal.valueOf(60));
            assertEquals(BigDecimal.valueOf(20),atm.getBalance());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void withdrawAllMoney() {
        atm.EnterAcctNum(customer60.card.AcctNum());
        atm.EnterPIN(customer60.PIN);
        try {
            atm.withdraw(BigDecimal.valueOf(60));
            assertEquals(BigDecimal.ZERO,atm.getBalance());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void withdrawInsufficientFunds() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        atm.EnterPIN(customer80.PIN);
        try {
            atm.withdraw(BigDecimal.valueOf(100));
            fail("Insuffient Funds");
        } catch (Exception e) {
            assertEquals(1,1);
        }

    }
    //endregion

    //region deposit
    @Test
    public void depositNoMoney() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        atm.EnterPIN(customer80.PIN);
        try {
            atm.deposit(BigDecimal.valueOf(0));
            assertEquals(BigDecimal.valueOf(80),atm.getBalance());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void depositMoney() {
        atm.EnterAcctNum(customer60.card.AcctNum());
        atm.EnterPIN(customer60.PIN);
        try {
            atm.deposit(BigDecimal.valueOf(40));
            assertEquals(BigDecimal.valueOf(100),atm.getBalance());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void depositInvalid() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        atm.EnterPIN(customer80.PIN);
        try {
            atm.deposit(BigDecimal.valueOf(-10));
            fail("Cannot deposit an invalid number");
        } catch (Exception e) {
            assertEquals(1,1);
        }
    }
    //endregion

    @Test
    public void AccountExists() {
        assertEquals(true, atm.EnterAcctNum(customer80.card.AcctNum()));
    }

    @Test
    public void AccountDoesntExists() {
        assertEquals(false, atm.EnterAcctNum(noExistCust.card.AcctNum()));
    }


    @Test
    public void ValidPIN() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        assertEquals(true, atm.EnterPIN(customer80.PIN));
    }

    @Test
    public void InvalidPIN() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        assertEquals(false, atm.EnterPIN(999999999));
    }

    @Test
    public void GetBalance() {
        atm.EnterAcctNum(customer80.card.AcctNum());
        atm.EnterPIN(customer80.PIN);
        try {
            assertEquals(BigDecimal.valueOf(80),atm.getBalance());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    

    //withdrawl
        //no money
        // invalid number
        //money
        //all money
        //isufficiemt funds

    //deposit
        //no moneu
        //some money
        //invalid num

    //account num doesnt exist
    //pin doesnt match acct
    //pin does match acct
    //retrieve balance
}