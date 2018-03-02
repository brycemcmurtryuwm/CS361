package com.haxorz.ChronoTimer;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class SystemClock {

	private static LocalTime _setTime = LocalTime.now();
	private static long _setMillisNow = System.currentTimeMillis();

	public static void setNow(String timeStr){
		setNow(LocalTime.parse(timeStr));
	}

	public static void setNow(LocalTime t){
		_setTime = t;

		_setMillisNow = System.currentTimeMillis();
	}

	public static LocalTime now(){
		long val = System.currentTimeMillis() - _setMillisNow;

		return _setTime.plus(val, ChronoUnit.MILLIS);
	}
}
