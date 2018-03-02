package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;
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
            case CLR:
                //TODO IMPLEMENT IN FUTURE
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
            }
        }
        else if(channelNum == 2){
            Athlete athlete = _didNotStartYet.poll();

            if(athlete != null){
                athlete.getTimeTracker(this.RunNumber).setEndTime(timeStamp);
            }

        }
    }
}
