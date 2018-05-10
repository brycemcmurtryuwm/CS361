package com.haxorz.ChronoTimer.Commands;

import com.haxorz.ChronoTimer.Hardware.SensorType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * links the given SensorType to the given challenge
 */
public class ConnectCmd extends CTCommand {

    public SensorType Sensor;
    public int channel;

    public ConnectCmd(SensorType type, int channel, LocalTime timestamp) {
        super(CmdType.CONN, timestamp);

        Sensor = type;
        this.channel = channel;
    }


    @Override
    public String ToString() {
        return this.TimeStamp.format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + " " + this.CMDType + " " + Sensor + " " + channel;
    }
}
