package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

public interface ChannelListener {

    void channelTriggered(int channelNum, LocalTime timeStamp);
}
