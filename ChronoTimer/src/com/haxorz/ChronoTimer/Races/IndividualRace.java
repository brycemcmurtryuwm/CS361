package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;
import com.haxorz.ChronoTimer.Commands.CancelCmd;
import com.haxorz.ChronoTimer.Commands.CmdType;
import com.haxorz.ChronoTimer.Commands.NumCmd;

import java.time.LocalTime;
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
                    athlete.getTimeTracker(this.RunNumber).setDNF(true);
                    RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " DNF");
                    _finished.add(athlete);
                }
                break;
            case NEWRUN:
            case ENDRUN:
                _didNotStartYet.clear();

                while ((athlete = _currentlyRacing.poll()) != null){
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
                RunRepository.addToCurrentRun("Athlete " + cancelCmd.AthleteNum + " CANCEL");

                if(!Race.COMPETITORS.containsKey(cancelCmd.AthleteNum))
                    return;

                Athlete a = Race.COMPETITORS.get(cancelCmd.AthleteNum);

                _currentlyRacing.remove(a);
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
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 1");
            }
            else
                RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 1");
        }
        else if(channelNum == 2){
            Athlete athlete = _didNotStartYet.poll();

            if(athlete != null){
                athlete.getTimeTracker(this.RunNumber).setEndTime(timeStamp);
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 2");
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(this.RunNumber).getDuration());
            }
            else
                RunRepository.addToCurrentRun("Athlete ??? TRIG Channel 2");

        }
    }
}
