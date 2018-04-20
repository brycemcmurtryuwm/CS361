package com.haxorz.ChronoTimer.Hardware;

import com.haxorz.ChronoTimer.Commands.CTCommand;

import java.io.PrintStream;
import java.time.LocalTime;

public class Printer {

    private boolean _printerOn = true;

    public HardwareEventListener Listener = null;
    private PrintStream out;

    public Printer(PrintStream out) {

        this.out = out;
    }

    public void connectPrinter(HardwareEventListener listener){
        Listener = listener;
    }

    public void PowerPushed(LocalTime timeStamp){
        if(Listener != null)
            Listener.hwEventTriggered(HWEventType.PrinterPwr, timeStamp);

        _printerOn = !_printerOn;
    }

    public void print(String toPrint){
        if(!_printerOn) return;

        out.print(toPrint);
    }


    public void log(CTCommand cmd) {
        if(!_printerOn) return;

        out.println(cmd.ToString());
    }
}
