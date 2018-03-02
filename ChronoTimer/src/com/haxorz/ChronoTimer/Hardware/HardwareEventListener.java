package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

public interface HardwareEventListener {

    void hwEventTriggered(HWEventType type, LocalTime timeStamp);
}
