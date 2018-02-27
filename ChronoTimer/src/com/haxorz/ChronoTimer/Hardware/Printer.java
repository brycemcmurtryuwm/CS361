package com.haxorz.ChronoTimer.Hardware;

public class Printer {

    public HardwareEventListener Listener = null;

    public void connectPrinter(HardwareEventListener listener){
        Listener = listener;
    }

    public void PowerPushed(){
        if(Listener != null)
            Listener.hwEventTriggered(HWEventType.PrinterPwr);
    }

    public void print(String toPrint){
        //abstraction for a printer
        //as we do not have a printer
    }




}
