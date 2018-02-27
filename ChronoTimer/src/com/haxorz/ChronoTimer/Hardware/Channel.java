package com.haxorz.ChronoTimer.Hardware;

public class Channel implements HardwareEventListener {


    //channel disable/enable, connect, trigger

    private boolean enabled;

    public void ToggleChannel(){
        enabled = !enabled;
    }

    public void Trigger(){
        if(enabled)
        {

        }
    }



    @Override
    public void hwEventTriggered(HWEventType type) {
        if(type == HWEventType.InputSensorTriggered){
            Trigger();
        }
    }
}
