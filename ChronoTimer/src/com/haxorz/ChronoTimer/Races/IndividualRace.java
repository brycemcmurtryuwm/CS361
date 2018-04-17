package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.*;

import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

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
            case SWAP:
                if(_currentlyRacing.size() < 2)
                    break;

                Collections.swap((LinkedList<Athlete>)_currentlyRacing,0,1);
                break;
            case CLR:
                ClearCmd clrCmd = (ClearCmd) cmd;
                RunRepository.addToCurrentRun("Athlete " + clrCmd.Num + " CLEAR\n");

                if(!Race.COMPETITORS.containsKey(clrCmd.Num))
                    return;

                a = Race.COMPETITORS.get(clrCmd.Num);
                _didNotStartYet.remove(a);
                break;
        }
    }


    @Override
    public void channelTriggered(int channelNum, LocalTime timeStamp) {
        if(channelNum == 1)
        {
            Athlete athlete = _didNotStartYet.poll();

            if(athlete != null){
                athlete.getTimeTracker(Race.RunNumber).setStartTime(timeStamp);
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 1\n");
                _currentlyRacing.add(athlete);
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
            }
            else
                RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 2\n");

        }
    }
}
