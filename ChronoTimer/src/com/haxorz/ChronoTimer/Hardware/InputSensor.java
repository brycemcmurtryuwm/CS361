package com.haxorz.ChronoTimer.Hardware;

public class InputSensor {

    public SensorType SensorType;

    public InputSensor(SensorType type){

        this.SensorType = type;
    }

    public HardwareEventListener Listener = null;

    public void connect(HardwareEventListener listener){
        Listener = listener;
    }

    public void Triggered(){
        if(Listener != null)
            Listener.hwEventTriggered(HWEventType.InputSensorTriggered);
    }


    public void disconnect() {
        Listener = null;
    }
}
