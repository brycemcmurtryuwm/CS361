package com.haxorz.ChronoTimer.Software;

import java.time.*;

public class raceTime {

	private LocalTime _startTime;
	private LocalTime _endTime;
	private boolean _DNF;

	//the name of the athlete will need to be changed if we
	//introduce an athlete class
	private String _athlete;


	public raceTime(String athlete) {
		_athlete = athlete;
	}

	public LocalTime getStartTime() { return _startTime; }
	public LocalTime getEndTime() {
		return _endTime;
	}
	public Duration getDuration(){
		if(_startTime != null && _endTime != null){
			return Duration.between(_startTime, _endTime);
		}
		throw new IllegalStateException("Start and/or End time not set");
	}
	public boolean isDNF() { return _DNF; }
	public String getAthlete() { return _athlete; }

	public void setAthlete(String athlete) { _athlete = athlete; }
	public void setDNF(boolean DNF) { this._DNF = DNF; }
	public void setStartTime(LocalTime startTime) { _startTime = startTime; }
	public void setEndTime(LocalTime endTime) {
		_endTime = endTime;
	}
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
		String str = _athlete + " :\t";
		if(_DNF){ return str + "DNF"; }

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
		String str = _athlete + " :\t";
		if(_DNF){ return str + "DNF"; }

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
