package com.haxorz.ChronoTimer;

import java.time.Duration;
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

	public static String toStringMinutes(Duration d){
		String str = "";

		long mins = (d.getSeconds() / 60);
		if(mins < 10) str += '0';
		str += mins;
		str += ':';

		long secs = d.getSeconds() % 60;
		if(secs < 10) str += '0';
		str += secs;
		str += '.';

		int hund = d.getNano() / 10000000; //10^7
		if(hund < 10) str += '0';
		str += hund;

		return str;
	}
}
