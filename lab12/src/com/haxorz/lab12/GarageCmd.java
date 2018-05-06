package com.haxorz.lab12;

public enum GarageCmd {
    DoorSafety, DoorLimit, DoorClick, LightClick, OFF;

    @Override
    public String toString() {
        switch (this){
            case DoorSafety:
                return "Door Safety Tripped";
            case DoorLimit:
                return "Door Limit Activated";
            case DoorClick:
                return "Door Click Pressed";
            case LightClick:
                return "Light Click Pressed";
            case OFF:
                return "Turning OFF";
        }
        return "";
    }
}
