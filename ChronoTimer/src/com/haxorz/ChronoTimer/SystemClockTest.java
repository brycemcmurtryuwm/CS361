package com.haxorz.ChronoTimer;

import static org.junit.Assert.*;
import org.junit.*;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class SystemClockTest {
	SystemClock sc;
	@Before
	public void setup(){
		sc = new SystemClock();
	}

	@Test
	public void test0() throws Exception {

		try {
			sc.setNow("fgtc");
			Assert.assertTrue(false);
		}
		catch(DateTimeParseException e){
			Assert.assertTrue(true);
		}
	}

	@Test
	public void test2() throws Exception {
		sc.setNow(LocalTime.now());
		sc.getClock().instant().compareTo(Instant.now());
	}

}
