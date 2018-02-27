package com.haxorz.ChronoTimer.Hardware;

public class InputSensor {

    public String Name;

    public InputSensor(String name){

        this.Name = name;
    }

    public HardwareEventListener Listener = null;

    public void connect(HardwareEventListener listener){
        Listener = listener;
    }

    public void Triggered(){
        if(Listener != null)
            Listener.hwEventTriggered(HWEventType.InputSensorTriggered);
    }


}
