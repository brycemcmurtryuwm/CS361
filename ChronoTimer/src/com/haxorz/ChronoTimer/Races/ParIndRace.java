package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.*;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class ParIndRace extends Race {

	private Queue<Athlete> _currentlyRacing1;
	private Queue<Athlete> _currentlyRacing2;

	private Queue<Athlete> _finished;
	private Queue<Athlete> _didNotStartYet;

	public ParIndRace() {
		_currentlyRacing1 = new LinkedList<>();
		_currentlyRacing2 = new LinkedList<>();
		_finished = new LinkedList<>();
		_didNotStartYet = new LinkedList<>();
	}

	@Override
	public RaceType getRaceType() {
		return RaceType.IND;
	}

	@Override
	public void executeCmd(CTCommand cmd) {
		switch (cmd.CMDType){
			case DNF:
				DNFCommand dnfcmd = (DNFCommand)cmd;

				//creates an athlete with the number of the one to be DNF'd
				Athlete athlete = new Athlete(dnfcmd.getAthleteNumber());

				athlete = COMPETITORS.get(athlete.getNumber());
				if(_currentlyRacing1.remove(athlete)) break;
				if(_currentlyRacing2.remove(athlete)) break;
				if(_didNotStartYet.remove(athlete)) break;

				//ensures that the athlete isn't in there twice
				_finished.remove(athlete);
				_finished.offer(athlete);
				break;
			case NEWRUN:
			case ENDRUN:
				_didNotStartYet.clear();

				while ((athlete = _currentlyRacing1.poll()) != null){
					athlete.getTimeTracker(this.RunNumber).setDNF(true);
				}
				while ((athlete = _currentlyRacing2.poll()) != null){
					athlete.getTimeTracker(this.RunNumber).setDNF(true);
				}
				_finished.clear();
				RunRepository.EndCurrentRun(this.RunNumber);

				if(cmd.CMDType == CmdType.ENDRUN)
					break;

				this.RunNumber++;
				break;
			case NUM:
				NumCmd numCmd = (NumCmd)cmd;

				if(COMPETITORS.containsKey(numCmd.Number)){
					athlete = COMPETITORS.get(numCmd.Number);
				}
				else {
					athlete = new Athlete(numCmd.Number);
					COMPETITORS.put(numCmd.Number, athlete);
				}

				athlete.registerForRace(this.RunNumber);
				_didNotStartYet.add(athlete);
				break;
			case CANCEL:
				CancelCmd cancelCmd = (CancelCmd) cmd;
				RunRepository.addToCurrentRun("Athlete " + cancelCmd.AthleteNum + " CANCEL\n");

				if(!Race.COMPETITORS.containsKey(cancelCmd.AthleteNum))
					return;

				Athlete a = Race.COMPETITORS.get(cancelCmd.AthleteNum);

				_currentlyRacing1.remove(a);
				_currentlyRacing2.remove(a);
				_didNotStartYet.remove(a);
				_finished.remove(a);

				a.discardRun(this.RunNumber);
				a.registerForRace(this.RunNumber);
				((LinkedList<Athlete>)_didNotStartYet).add(0,a);
				break;
			case SWAP:
				//TODO IMPLEMENT IN FUTURE NOT NEEDED IN SPRINT 1
				break;
			case CLR:
				//TODO IMPLEMENT IN FUTURE NOT NEEDED IN SPRINT 1
				break;
		}
	}


	@Override
	public void channelTriggered(int channelNum, LocalTime timeStamp) {
		if(channelNum == 1)
		{
			Athlete athlete = _didNotStartYet.poll();

			if(athlete != null){
				athlete.getTimeTracker(this.RunNumber).setStartTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 1\n");
				_currentlyRacing1.add(athlete);
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 1\n");
		}
		else if(channelNum == 2){
			Athlete athlete = _currentlyRacing1.poll();

			if(athlete != null){
				athlete.getTimeTracker(this.RunNumber).setEndTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 2\n");
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(this.RunNumber).toStringMinutes() + "\n");
				_finished.add(athlete);
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 2\n");

		}
		if(channelNum == 3)
		{
			Athlete athlete = _didNotStartYet.poll();

			if(athlete != null){
				athlete.getTimeTracker(this.RunNumber).setStartTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 3\n");
				_currentlyRacing2.add(athlete);
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 3\n");
		}
		else if(channelNum == 4){
			Athlete athlete = _currentlyRacing2.poll();

			if(athlete != null){
				athlete.getTimeTracker(this.RunNumber).setEndTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 4\n");
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(this.RunNumber).toStringMinutes() + "\n");
				_finished.add(athlete);
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 4\n");

		}
	}
}

