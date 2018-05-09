package com.haxorz.ChronoTimer;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * the class that takes care of the actual timekeeping of
 * the chronoTimer
 */
public class SystemClock {

	private static LocalTime _setTime = LocalTime.now();
	private static long _setMillisNow = System.currentTimeMillis();

	/**
	 * @param timeStr the text representation of the
	 *       			current time such as "10:15:30", not null
	 */
	public static void setNow(String timeStr){
		setNow(LocalTime.parse(timeStr));
	}

	/**
	 * takes a LocalTime and sets the sytem time to that
	 *
	 * @param t time to be set as the time now
	 */
	public static void setNow(LocalTime t){
		_setTime = t;

		_setMillisNow = System.currentTimeMillis();
	}

	/**
	 * @return sets the clock to the current time
	 */
	public static LocalTime now(){
		long val = System.currentTimeMillis() - _setMillisNow;

		return _setTime.plus(val, ChronoUnit.MILLIS);
	}

	/**
	 * @param d a duration object that will be turned into a string
	 * @return a string representation of the string passed in
	 */
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
