package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.*;

import java.time.LocalTime;
import java.util.*;

/**
 * one start, multiple finishes
 * used in sports such as track and field sprints
 *
 * up to 8 racers
 */
public class ParGrpRace extends Race {

	private Athlete[] _group = new Athlete[8];
	private LocalTime _startTime = null;
	private List<Athlete> _finished = new ArrayList<>();

	@Override
	public RaceType getRaceType() {
		return RaceType.PARGRP;
	}

	@Override
	public void executeCmd(CTCommand cmd) {
		switch (cmd.CMDType){
			case DNF:
				DNFCommand dnfcmd = (DNFCommand)cmd;

				if(_startTime == null) return;

				if(dnfcmd.getLane()<0 || dnfcmd.getLane() > 8) break;

				Athlete athlete = _group[dnfcmd.getLane()-1];

				if(athlete != null){
					athlete.getTimeTracker(Race.RunNumber).setDNF(true);
					RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " DNF\n");
					_group[dnfcmd.getLane()-1] = null;
					_finished.add(athlete);
				}
				break;
			case NEWRUN:
				//newRun is an extension of endRun, thus no break
			case ENDRUN:
				_startTime = null;

				//set DNF to any racers who haven't finished
				for (int i = 0; i < _group.length; i++) {
					Athlete a_group = _group[i];
					if (a_group != null)
						a_group.getTimeTracker(Race.RunNumber).setDNF(true);
					_group[i] = null;
				}

				_finished.clear();
				RunRepository.EndCurrentRun(Race.RunNumber);

				if(cmd.CMDType == CmdType.ENDRUN)
					break;

				Race.RunNumber++;
				break;
			//add athlete
			case NUM:
				int nullCount = 0;

				for (Athlete a : _group){
					if (a == null)
						nullCount++;
				}

				if(nullCount == 0 || _startTime != null){
					//if more than 8 are added we are just to ignore
					return;
				}
				NumCmd numCmd = (NumCmd)cmd;
				Athlete a;
				if(COMPETITORS.containsKey(numCmd.Number)){
					a = COMPETITORS.get(numCmd.Number);
				}
				else{
					a = new Athlete(numCmd.Number);
					COMPETITORS.put(numCmd.Number, a);
				}

				if(a.registeredForRace(Race.RunNumber))
					return;

				a.registerForRace(Race.RunNumber);

				for (int i = 0; i < _group.length; i++) {
					if(_group[i] == null){
						_group[i] = a;
						break;
					}
				}
				break;
			case CANCEL:
				//not in this race type
				break;
			case SWAP:
				//no swap in pargrp
				break;
			case CLR:
				//not in this type
				break;
		}
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	protected List<Athlete> getCompletedAthletes() {
		return new ArrayList<>(_finished);
	}

	//returns athletes running
	@Override
	public List<Athlete> athletesRunning() {
		if(_startTime == null)
			return  new ArrayList<>();

		 ArrayList<Athlete> toReturn = new ArrayList<>();

		 for (Athlete a : _group){
		 	if(a != null)
		 		toReturn.add(a);
		 }
		 return toReturn;
	}

	@Override
	protected List<Athlete> athletesInQueue() {
		if(_startTime != null)
			return new ArrayList<>();

		ArrayList<Athlete> toReturn = new ArrayList<>();

		for (Athlete a : _group){
			if(a != null)
				toReturn.add(a);
		}
		return toReturn;}


	/**
	 * @param channelNum channel triggered, 1 to start the race, after that the
	 *                   channel coresponds to the lane of the racer
	 * @param timeStamp
	 */
	@Override
	public void channelTriggered(int channelNum, LocalTime timeStamp) {
		if(channelNum == 1 && _startTime == null)
		{
			_startTime = timeStamp;
			for(Athlete a:_group) {
				if(a == null)
					continue;

				a.getTimeTracker(Race.RunNumber).setStartTime(timeStamp);
			}

			RunRepository.addToCurrentRun("PARGRP race started\n");
			this.setChanged();
			this.notifyObservers();
			return;
		}
		if(_startTime == null) return;
		if(channelNum > 8) return;

		Athlete athlete = _group[channelNum - 1];
		if(athlete != null){
			athlete.getTimeTracker(Race.RunNumber).setEndTime(timeStamp);
			RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel" + channelNum + " \n");
			RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(Race.RunNumber).toStringMinutes() + "\n");
			_group[channelNum - 1] = null;
			_finished.add(athlete);
			this.setChanged();
			this.notifyObservers();
		}

		int nullCount = 0;

		for (Athlete a : _group){
			if (a == null)
				nullCount++;
		}

		if(nullCount == 8)
			_startTime = null;
	}
}

