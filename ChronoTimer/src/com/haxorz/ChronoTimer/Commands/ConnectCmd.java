package com.haxorz.ChronoTimer.Commands;

import com.haxorz.ChronoTimer.Hardware.SensorType;

import java.time.LocalTime;

public class ConnectCmd extends CTCommand {

    public SensorType Sensor;
    public int channel;

    public ConnectCmd(SensorType type, int channel, LocalTime timestamp) {
        super(CMDType.CONN, timestamp);

        Sensor = type;
        this.channel = channel;
    }


    @Override
    public String ToString() {
        return this.TimeStamp + " " + this.CMDType + " " + Sensor + " " + channel;
    }
}
