package com.haxorz.lab12;

import java.io.PrintStream;

public class GarageDoorSystem {

    private Light _light = new Light();
    private GarageDoor _door = new GarageDoor();
    private PrintStream _out;

    public GarageDoorSystem(PrintStream out) {

        _out = out;
    }

    public void execute(GarageCmd cmd) {

        switch (cmd){
            case DoorSafety:
                _door.onSafety();
                break;
            case DoorLimit:
                _door.onLimit();
                break;
            case DoorClick:
                if(getState() == DoorState.CLOSED)
                {
                    //turn on light
                    _light.autoON();
                }
                _door.onClick();
                break;
            case LightClick:
                _light.onClick();
                break;
            case OFF:
                Driver.STOP = true;
                break;
        }

        _out.println("Event: " + cmd.toString() + "\n");
        _out.println("Current State: \n" );
        _out.println(_door.toString());
        _out.println(_light.toString());

        
    }

    public DoorState getState() {
        return _door.getState();
    }
}
