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

	private ArrayList<Athlete> _group;
	private boolean _started;

	//athletes are copied to finished after they finish
	//but remain in _group
	private List<Athlete> _finished;

	public ParGrpRace() {
		_group = new ArrayList<>(8);
		_started = false;
		Athlete[] arr = new Athlete[8];
		_finished = Arrays.asList(arr);

	}

	@Override
	public RaceType getRaceType() {
		return RaceType.PARGRP;
	}

	@Override
	public void executeCmd(CTCommand cmd) {
		switch (cmd.CMDType){
			case DNF:
				//no function in this race type
				break;
			case NEWRUN:
				//newRun is an extension of endRun, thus no break
			case ENDRUN:
				_started = false;

				//set DNF to any racers who haven't finished
				for(int i = 0; i < _group.size(); i++) {
					if(_finished.get(i) == null){
						_group.get(i).getTimeTracker(Race.RunNumber).setDNF(true);
					}
				}

				//clear everything
				_group.clear();
				for(Athlete a:_finished) {
					a = null;
				}
				_started = false;


				RunRepository.EndCurrentRun(Race.RunNumber);

				if(cmd.CMDType == CmdType.ENDRUN)
					break;

				Race.RunNumber++;
				break;
			//add athlete
			case NUM:
				if(_group.size() > 8){
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

				_group.add(a);

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
		return (List<Athlete>)_group.clone();
	}

	@Override
	protected List<Athlete> athletesInQueue() {
		return _started? new ArrayList<Athlete>():_group;
	}


	/**
	 * @param channelNum channel triggered, 1 to start the race, after that the
	 *                   channel coresponds to the lane of the racer
	 * @param timeStamp
	 */
	@Override
	public void channelTriggered(int channelNum, LocalTime timeStamp) {
		if(channelNum == 1 && !_started)
		{
			_started = true;
			for(Athlete a:_group) {
				a.getTimeTracker(Race.RunNumber).setStartTime(timeStamp);
			}

			RunRepository.addToCurrentRun("PARIND race started\n");
			this.setChanged();
			this.notifyObservers();
			return;
		}
		if(!_started) return;
		if(channelNum > _group.size()) return;

		Athlete athlete = _group.get(channelNum);
		if(athlete == null){
			RunRepository.addToCurrentRun("Athlete ??? TRIG Channel" + channelNum + "\n");
		}
		else{
			RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(Race.RunNumber).toStringMinutes() + "\n");
			_finished.add(athlete);
		}
		this.setChanged();
		this.notifyObservers();



	}
}

