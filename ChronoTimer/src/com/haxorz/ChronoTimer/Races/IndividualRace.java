package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;

public class IndividualRace extends Race {

    @Override
    public RaceType getRaceType() {
        return RaceType.IND;
    }

    @Override
    public void executeCmd(CTCommand cmd) {

        switch (cmd.CMDType){
            case TOG:
                break;
            case DNF:
                break;
            case TRIG:
                break;
            case START:
                break;
            case FINISH:
                break;
            case NEWRUN:
                break;
            case ENDRUN:
                break;
            case NUM:
                break;
        }

    }

}
