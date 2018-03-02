package com.haxorz.ChronoTimer.Races;

import java.time.Duration;
import java.time.LocalTime;

public class RaceTime {

	private LocalTime _startTime;
	private LocalTime _endTime;
	private boolean _DNF = false;

	private Athlete _athlete;


	public RaceTime(Athlete athlete) {
		_athlete = athlete;
	}

	public LocalTime getStartTime() { return _startTime; }
	public void setStartTime(LocalTime startTime) { _startTime = startTime; }

	public LocalTime getEndTime() { return _endTime;}
	public void setEndTime(LocalTime endTime) {	_endTime = endTime;	}

	public Duration getDuration(){
		if(_startTime != null && _endTime != null){
			return Duration.between(_startTime, _endTime);
		}
		throw new IllegalStateException("Start and/or End time not set");
	}

	public boolean isDNF() { return _DNF; }
	public void setDNF(boolean DNF) { this._DNF = DNF; }
	public void setStartNow(){
		_startTime = LocalTime.now();
	}
	public void setEndNow(){
		_endTime = LocalTime.now();
	}

	/**
	 * @return a string with the race time with hours, minutes, seconds and hundredths of a second
	 */
	public String toStringHours(){
		String str = "";

		Duration d = this.getDuration();

		long hours = d.getSeconds() / 3600;
		str += hours;
		str += ':';

		long mins = (d.getSeconds() / 60) % 60;
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

	/**
	 * @return string with minutes, seconds and hundredths of a secons
	 */
	public String toStringMinutes(){
		String str = "";

		Duration d = this.getDuration();

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
