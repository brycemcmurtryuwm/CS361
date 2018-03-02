package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

public class InputSensor {

    public SensorType SensorType;

    public InputSensor(SensorType type){

        this.SensorType = type;
    }

    public HardwareEventListener Listener = null;

    public void connect(HardwareEventListener listener){
        Listener = listener;
    }

    public void Triggered(LocalTime timeStamp){
        if(Listener != null)
            Listener.hwEventTriggered(HWEventType.InputSensorTriggered, timeStamp);
    }


    public void disconnect() {
        Listener = null;
    }
}
