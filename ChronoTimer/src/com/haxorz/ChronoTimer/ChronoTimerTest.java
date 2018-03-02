package com.haxorz.ChronoTimer;

import static org.junit.Assert.*;
import org.junit.*;



public class SystemClockTest {
	ChronoTimer ct;

	@Before
	public void setup(){
		ct = new ChronoTimer(System.out);
	}

	@Test
	public void test0() throws Exception {
		ct.

	}

	@Test
	public void test1() throws Exception {
		sc.setNow(LocalTime.now());
		sc.getClock().instant().compareTo(Instant.now())
	}

}