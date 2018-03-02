package com.haxorz.ChronoTimer;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Clock;

import static java.time.Clock.offset;

public class SystemClock {
	Clock _clock;

	public void setNow(String timeStr){
		setNow(LocalTime.parse(timeStr));
	}

	public void setNow(LocalTime t){
		LocalTime systime = LocalTime.now();

		//this will be negative half the time, as
		//it should be, if we have a negative offset
		Duration os = Duration.between(systime, t);

		_clock = offset(Clock.systemDefaultZone(), os);
	}

	public Clock getClock() {
		return _clock;
	}

	public void setClock(Clock clock) {
		_clock = clock;
	}
}
