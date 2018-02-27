package com.haxorz.lab5;

public class Card{

    private long acctNum;

    public Card(long acctNum){
        this.acctNum = acctNum;
    }



    public static class CardReader {
        //account Num
        public static long acctNumber(Card card){
            return card.acctNum;
        }

    }
}

