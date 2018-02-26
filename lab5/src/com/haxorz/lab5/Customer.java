package com.haxorz.lab5;

public class Customer {

    public CardReader.Card card;
    public int PIN;

    public Customer (CardReader.Card card, int pin){
        this.card = card;
        this.PIN = pin;
    }

}
