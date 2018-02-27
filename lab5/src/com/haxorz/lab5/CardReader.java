package com.haxorz.lab5;

public class CardReader{



    public long acctNumber(Card card){
        return card.acctNum;
    }

    public class Card {

        //account Num

        private long acctNum;

        public Card(long acctNum){
            this.acctNum = acctNum;
        }



    }
}

