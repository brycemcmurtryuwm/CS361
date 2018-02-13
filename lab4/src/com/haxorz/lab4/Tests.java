package com.haxorz.lab4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class Tests {
    private Customer customer80;
    private Customer customer0;
    private Customer customer100;
    private ATM atm;
    private Bank bank;

    @Before
    public void setup(){
        customer80 = new Customer(new Card(12345), new Account(1234,000,80));
        customer0 = new Customer(new Card(12345), new Account(1235,000,0));
        customer100 = new Customer(new Card(12345), new Account(1236,000,100));

        atm = new ATM();
        bank = new Bank();
    }

    @Test
    public void testOneThrow() {
        //test a single throw and check scoring for the first frame and game
        double amountWithacct.withdrawl();

        try {
            scoreSheet.throwBall(ballThrow.EIGHT);
        } catch (IllegalThrowException e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(8, scoreSheet.getFrameScore(0));
            assertEquals(8, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
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
    //retiree balance



    //




























    @Test
    public void testOneThrow() {
        //test a single throw and check scoring for the first frame and game
        try {
            scoreSheet.throwBall(ballThrow.EIGHT);
        } catch (IllegalThrowException e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(8, scoreSheet.getFrameScore(0));
            assertEquals(8, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testTwoThrows() {
        //test two throws and check scoring for the first frame and game
        try {
            scoreSheet.throwBall(ballThrow.EIGHT);
            scoreSheet.throwBall(ballThrow.ONE);
        } catch (IllegalThrowException e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(9, scoreSheet.getFrameScore(0));
            assertEquals(9, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testThreeThrows() {
        //test three throws and check scoring for the first two frames and game
        try {
            scoreSheet.throwBall(ballThrow.EIGHT);
            scoreSheet.throwBall(ballThrow.ONE);
            scoreSheet.throwBall(ballThrow.NINE);
        } catch (IllegalThrowException e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(9, scoreSheet.getFrameScore(0));
            assertEquals(9, scoreSheet.getFrameScore(1));
            assertEquals(18, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSpareCountsNextFrameScore() {
        //throw a spare in a frame and make sure its score is correct (counting the following frame, which should also be completed)
        try {
            scoreSheet.throwBall(ballThrow.EIGHT);
            scoreSheet.throwBall(ballThrow.TWO);
            scoreSheet.throwBall(ballThrow.NINE);
            scoreSheet.throwBall(ballThrow.ZERO);
        } catch (IllegalThrowException e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(19, scoreSheet.getFrameScore(0));
            assertEquals(9, scoreSheet.getFrameScore(1));
            assertEquals(28, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testStrikeMovesToNextFrame() {
        //ensure that a strike frame may not have two throws
        try {
            scoreSheet.throwBall(ballThrow.STRIKE);
            scoreSheet.throwBall(ballThrow.TWO);

        } catch (IllegalThrowException e) {
            fail(e.getMessage());
        }

        try {
            assertEquals(12, scoreSheet.getFrameScore(0));
            assertEquals(2, scoreSheet.getFrameScore(1));
            assertEquals(14, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }
    }

    //-----------------------------------------------------------------------------


    @Test
    public void testStrikeCountsNextFrameScores() //- throw a strike in a frame and make sure itsscore is correct (counting the following frames, which should also be completed)
    {
        try{
            scoreSheet.throwBall(ballThrow.STRIKE);
            scoreSheet.throwBall(ballThrow.STRIKE);
            scoreSheet.throwBall(ballThrow.STRIKE);
        }
        catch(IllegalThrowException e){
            fail(e.getMessage());
        }

        try {
            assertEquals(30, scoreSheet.getFrameScore(0));
            assertEquals(20, scoreSheet.getFrameScore(1));
            assertEquals(10, scoreSheet.getFrameScore(2));
            assertEquals(60, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSpareOnLastFrame() //- test throwing a spare on the 10th frame
    {
        for(int i = 0; i < 9; ++i) {
            try {
                scoreSheet.throwBall(ballThrow.STRIKE);
            } catch(IllegalThrowException e) {
                fail("Illegal throw exception");
            }
        }
        for(int i = 0; i < 2; ++i) {
            try {
                scoreSheet.throwBall(ballThrow.FIVE);
            } catch(IllegalThrowException e) {
                fail("Illegal throw exception");
            }
        }
        try{
            assertEquals(10, scoreSheet.getFrameScore(9));
            assertEquals(270, scoreSheet.getTotalScore());
        }
        catch(IllegalScoreException e){
            fail("illegal score exception");
        }


    }
    @Test
    public void testStrikeOnLastFrames() //- test throwing a strike on the 8th, 9th, and 10th frames
    {
        for(int i = 0; i < 10; ++i) {
            try {
                scoreSheet.throwBall(ballThrow.STRIKE);
            } catch(IllegalThrowException e) {
                fail("Illegal throw exception");
            }
        }
        try{
            assertEquals(30, scoreSheet.getFrameScore(7));
            assertEquals(20, scoreSheet.getFrameScore(8));
            assertEquals(10, scoreSheet.getFrameScore(9));
            assertEquals(270, scoreSheet.getTotalScore());
        }
        catch(IllegalScoreException e){
            fail("illegal score exception");
        }
    }
    @Test
    public void testThrowOn11thFrame() //- ensure throwing on the 11th frame is not allowed (in some form)
    {
        for(int i = 0; i < 10; ++i) {
            try {
                scoreSheet.throwBall(ballThrow.STRIKE);
            } catch(IllegalThrowException e) {
                fail("Illegal throw exception on throw " + i);
            }
        }
        try {
            scoreSheet.throwBall(ballThrow.STRIKE);
            fail("Fail: allowed an eleventh throw");
        } catch(IllegalThrowException e) {
            assertEquals(1,1);
        }

        try {
            assertEquals(270, scoreSheet.getTotalScore());
        } catch (IllegalScoreException e) {
            fail(e.getMessage());
        }
    }
}