package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.*;

import java.time.LocalTime;
import java.util.*;

public class ParIndRace extends Race {

	private Queue<Athlete> _currentlyRacing1;
	private Queue<Athlete> _currentlyRacing2;

	private Queue<Athlete> _finished;
	private Queue<Athlete> _didNotStartYet1;
	private Queue<Athlete> _didNotStartYet2;

	public ParIndRace() {
		_currentlyRacing1 = new LinkedList<>();
		_currentlyRacing2 = new LinkedList<>();
		_finished = new LinkedList<>();
		_didNotStartYet1 = new LinkedList<>();
		_didNotStartYet2 = new LinkedList<>();
	}

	@Override
	public RaceType getRaceType() {
		return RaceType.PARIND;
	}

	@Override
	public void executeCmd(CTCommand cmd) {
		switch (cmd.CMDType){
			case DNF:
				DNFCommand dnfcmd = (DNFCommand)cmd;

				/*//creates an athlete with the number of the one to be DNF'd
				Athlete athlete = new Athlete(dnfcmd.getAthleteNumber());

				athlete = COMPETITORS.get(athlete.getNumber());
				if(_currentlyRacing1.remove(athlete)) break;
				if(_currentlyRacing2.remove(athlete)) break;
				if(_didNotStartYet.remove(athlete)) break;

				//ensures that the athlete isn't in there twice
				_finished.remove(athlete);
				_finished.offer(athlete);*/
				if(dnfcmd.getLane() != 1 && dnfcmd.getLane() != 2) break;

				Athlete athlete = (dnfcmd.getLane() == 1)?_currentlyRacing1.poll(): _currentlyRacing2.poll();

				if(athlete != null){
					athlete.getTimeTracker(Race.RunNumber).setDNF(true);
					RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " DNF\n");
					_finished.add(athlete);
				}
				break;
			case NEWRUN:
			case ENDRUN:
				_didNotStartYet1.clear();
				_didNotStartYet2.clear();

				while ((athlete = _currentlyRacing1.poll()) != null){
					athlete.getTimeTracker(Race.RunNumber).setDNF(true);
				}
				while ((athlete = _currentlyRacing2.poll()) != null){
					athlete.getTimeTracker(Race.RunNumber).setDNF(true);
				}
				_finished.clear();
				RunRepository.EndCurrentRun(Race.RunNumber);

				if(cmd.CMDType == CmdType.ENDRUN)
					break;

				Race.RunNumber++;
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

				athlete.registerForRace(Race.RunNumber);

				if (_didNotStartYet2.size() < _didNotStartYet1.size()) {
					_didNotStartYet2.add(athlete);
				} else {
					_didNotStartYet1.add(athlete);
				}
				break;
			case CANCEL:
				CancelCmd cancelCmd = (CancelCmd) cmd;
				RunRepository.addToCurrentRun("Athlete " + cancelCmd.AthleteNum + " CANCEL\n");

				if(!Race.COMPETITORS.containsKey(cancelCmd.AthleteNum))
					return;

				Athlete a = Race.COMPETITORS.get(cancelCmd.AthleteNum);

				_currentlyRacing1.remove(a);
				_currentlyRacing2.remove(a);
				_didNotStartYet1.remove(a);
				_didNotStartYet2.remove(a);
				_finished.remove(a);

				a.discardRun(Race.RunNumber);
				a.registerForRace(Race.RunNumber);

				if (_didNotStartYet2.size() < _didNotStartYet1.size()) {
					((LinkedList<Athlete>)_didNotStartYet2).add(0,a);
				} else {
					((LinkedList<Athlete>)_didNotStartYet1).add(0,a);
				}
				break;
			case SWAP:
				SwapCmd swap = (SwapCmd)cmd;
				if(swap.ChannelNum == 1){
					if(_currentlyRacing1.size() < 2)
						break;

					Collections.swap((LinkedList<Athlete>)_currentlyRacing1,0,1);
				}
				else {
					if(_currentlyRacing2.size() < 2)
						break;

					Collections.swap((LinkedList<Athlete>)_currentlyRacing2,0,1);
				}
				break;
			case CLR:
				ClearCmd clrCmd = (ClearCmd) cmd;
				RunRepository.addToCurrentRun("Athlete " + clrCmd.Num + " CLEAR\n");

				if(!Race.COMPETITORS.containsKey(clrCmd.Num))
					return;

				a = Race.COMPETITORS.get(clrCmd.Num);
				_didNotStartYet1.remove(a);
				_didNotStartYet2.remove(a);
				break;
		}
		this.setChanged();
		this.notifyObservers();
	}

    @Override
    protected List<Athlete> getCompletedAthletes() {
        return new ArrayList<>(_finished);
    }

    @Override
    public List<Athlete> athletesRunning() {
		List<Athlete> toReturn = new ArrayList<>();

		for (int i = 0; i < _currentlyRacing1.size(); i++) {
			toReturn.add(((LinkedList<Athlete>)_currentlyRacing1).get(i));
			if(i<_currentlyRacing2.size())
				toReturn.add(((LinkedList<Athlete>)_currentlyRacing2).get(i));
		}
		return toReturn;
    }

    @Override
    protected List<Athlete> athletesInQueue() {
        List<Athlete> toReturn = new ArrayList<>();

		for (int i = 0; i < _didNotStartYet1.size(); i++) {
			toReturn.add(((LinkedList<Athlete>)_didNotStartYet1).get(i));
			if(i<_didNotStartYet2.size())
				toReturn.add(((LinkedList<Athlete>)_didNotStartYet2).get(i));
		}
        return toReturn;
    }


    @Override
	public void channelTriggered(int channelNum, LocalTime timeStamp) {
		if(channelNum == 1)
		{
			Athlete athlete = _didNotStartYet1.poll();

			if(athlete != null){
				athlete.getTimeTracker(Race.RunNumber).setStartTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 1\n");
				_currentlyRacing1.add(athlete);
				this.setChanged();
				this.notifyObservers();
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 1\n");
		}
		else if(channelNum == 2){
			Athlete athlete = _currentlyRacing1.poll();

			if(athlete != null){
				athlete.getTimeTracker(Race.RunNumber).setEndTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 2\n");
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(Race.RunNumber).toStringMinutes() + "\n");
				_finished.add(athlete);
				this.setChanged();
				this.notifyObservers();
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 2\n");

		}
		if(channelNum == 3)
		{
			Athlete athlete = _didNotStartYet2.poll();

			if(athlete != null){
				athlete.getTimeTracker(Race.RunNumber).setStartTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 3\n");
				_currentlyRacing2.add(athlete);
				this.setChanged();
				this.notifyObservers();
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 3\n");
		}
		else if(channelNum == 4){
			Athlete athlete = _currentlyRacing2.poll();

			if(athlete != null){
				athlete.getTimeTracker(Race.RunNumber).setEndTime(timeStamp);
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 4\n");
				RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(Race.RunNumber).toStringMinutes() + "\n");
				_finished.add(athlete);
				this.setChanged();
				this.notifyObservers();
			}
			else
				RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 4\n");

		}
	}
}

