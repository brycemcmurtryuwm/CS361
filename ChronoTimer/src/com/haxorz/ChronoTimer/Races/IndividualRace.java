package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.*;

import java.time.LocalTime;
import java.util.*;

/**
 * An atlete starts and is addeded to a queue of currently racing
 * Thus, the first person to finish is assumed to be the first
 * person who started (unless there is a DNF command)
 */
public class IndividualRace extends Race {

    private Queue<Athlete> _currentlyRacing;
    private Queue<Athlete> _finished;
    private Queue<Athlete> _didNotStartYet;

    public IndividualRace() {
        _currentlyRacing = new LinkedList<>();
        _finished = new LinkedList<>();
        _didNotStartYet = new LinkedList<>();


    }

    @Override
    public RaceType getRaceType() {
        return RaceType.IND;
    }

    /**
     * @param cmd command object parsed from CTCommand
     */
    @Override
    public void executeCmd(CTCommand cmd) {
        switch (cmd.CMDType){
            case DNF:
                Athlete athlete = _currentlyRacing.poll();

                if(athlete != null){
                    athlete.getTimeTracker(Race.RunNumber).setDNF(true);
                    RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " DNF\n");
                    _finished.add(athlete);
                }
                break;
            case NEWRUN:
                //an extension of endRun, thus no break
            case ENDRUN:
                _didNotStartYet.clear();

                while ((athlete = _currentlyRacing.poll()) != null){
                    athlete.getTimeTracker(Race.RunNumber).setDNF(true);
                }
                _finished.clear();
                RunRepository.EndCurrentRun(Race.RunNumber);

                if(cmd.CMDType == CmdType.ENDRUN)
                    break;

                Race.RunNumber++;
                break;
            //add a racer to the race
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
                _didNotStartYet.add(athlete);
                break;
            //remove racer from race
            case CANCEL:
                CancelCmd cancelCmd = (CancelCmd) cmd;
                RunRepository.addToCurrentRun("Athlete " + cancelCmd.AthleteNum + " CANCEL\n");

                if(!Race.COMPETITORS.containsKey(cancelCmd.AthleteNum))
                    return;

                Athlete a = Race.COMPETITORS.get(cancelCmd.AthleteNum);
                _currentlyRacing.remove(a);
                _didNotStartYet.remove(a);
                _finished.remove(a);

                a.discardRun(Race.RunNumber);
                a.registerForRace(Race.RunNumber);
                ((LinkedList<Athlete>)_didNotStartYet).add(0,a);
                break;
            //for the case when a later racer outruns an earlier one
            case SWAP:
                if(_currentlyRacing.size() < 2)
                    break;

                Collections.swap((LinkedList<Athlete>)_currentlyRacing,0,1);
                break;
            //clears
            case CLR:
                ClearCmd clrCmd = (ClearCmd) cmd;
                RunRepository.addToCurrentRun("Athlete " + clrCmd.Num + " CLEAR\n");

                if(!Race.COMPETITORS.containsKey(clrCmd.Num))
                    return;

                a = Race.COMPETITORS.get(clrCmd.Num);
                _didNotStartYet.remove(a);
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
        return new ArrayList<>(_currentlyRacing);
    }

    @Override
    protected List<Athlete> athletesInQueue() {
        return new ArrayList<>(_didNotStartYet);
    }


    /**
     * @param channelNum channel to be triggered, 1 is start, 2 is end
     * @param timeStamp time this channel is triggered
     */
    @Override
    public void channelTriggered(int channelNum, LocalTime timeStamp) {
        if(channelNum == 1)
        {
            Athlete athlete = _didNotStartYet.poll();

            if(athlete != null){
                athlete.getTimeTracker(Race.RunNumber).setStartTime(timeStamp);
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 1\n");
                _currentlyRacing.add(athlete);
                this.setChanged();
                this.notifyObservers();
            }
            else
                RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 1\n");
        }
        else if(channelNum == 2){
            Athlete athlete = _currentlyRacing.poll();

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
    }
}
