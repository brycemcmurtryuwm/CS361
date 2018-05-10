package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

/**
 * interface of components that can listen for trigger commands
 */
public interface ChannelListener {

    void channelTriggered(int channelNum, LocalTime timeStamp);
}
