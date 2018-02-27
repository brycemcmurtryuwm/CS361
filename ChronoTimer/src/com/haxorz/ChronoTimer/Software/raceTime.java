package com.haxorz.ChronoTimer.Software;

import java.time.*;

public class raceTime {

	private LocalTime _startTime;
	private LocalTime _endTime;
	private boolean _DNF;

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
	 * @return a string with the race time with hours place
	 */
//	public String toStringHours(){
//		if(_DNF){
//			return "DNF";
//		}
//		else{
//			String str = "";
//			Duration d = this.getDuration();
//			str +=  d.getSeconds() / 3600;
//
//			return str;
//		}
//	}
}
