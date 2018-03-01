package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.CTCommand;

public class IndividualRace extends Race {

    @Override
    public RaceType getRaceType() {
        return RaceType.IND;
    }

    @Override
    public void executeCmd(CTCommand cmd) {

    }
}
