package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

/**
 * Interface of devices that can listen for hardware events
 */
public interface HardwareEventListener {

    void hwEventTriggered(HWEventType type, LocalTime timeStamp);
}
