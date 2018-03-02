package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

public class Printer {

    private boolean _printerOn = false;

    public HardwareEventListener Listener = null;

    public void connectPrinter(HardwareEventListener listener){
        Listener = listener;
    }

    public void PowerPushed(LocalTime timeStamp){
        if(Listener != null)
            Listener.hwEventTriggered(HWEventType.PrinterPwr, timeStamp);

        _printerOn = !_printerOn;
    }

    public void print(String toPrint){
        //abstraction for a printer
        //as we do not have a printer
    }




}
