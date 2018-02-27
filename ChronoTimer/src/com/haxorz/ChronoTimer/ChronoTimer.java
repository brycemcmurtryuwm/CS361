package com.haxorz.ChronoTimer;

import com.haxorz.ChronoTimer.Commands.CTCommand;

public class ChronoTimer {

    private boolean poweredOn = false;

    public void executeCmd(CTCommand cmd){

        if(!poweredOn)
            return;

    }
}
