package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

/**
 * the channel that has a number and can be triggered to have an action in an event
 */
public class Channel implements HardwareEventListener {

    private int channelNum;
    private boolean enabled = false;
    public static ChannelListener ChannelListener = null;

    public Channel(int channelNum) {

        this.channelNum = channelNum;
    }

    public boolean ToggleChannel(){
        enabled = !enabled;
        return enabled;
    }

    public void Trigger(LocalTime timeStamp){
        if(enabled && ChannelListener != null){
            ChannelListener.channelTriggered(channelNum, timeStamp);
        }
    }
    @Override
    public void hwEventTriggered(HWEventType type, LocalTime timeStamp) {
        if(type == HWEventType.InputSensorTriggered){
            Trigger(timeStamp);
        }
    }
}
