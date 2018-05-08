package com.haxorz.ChronoTimer.Hardware;

import com.haxorz.ChronoTimer.Commands.CTCommand;

import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Printer {

    private boolean _printerOn = true;

    public HardwareEventListener Listener = null;
    private PrintStream out;
    private List<String> _runnningLog = new ArrayList<>();

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

    public void printLog(){
        if(!_printerOn) return;

        for(String s:_runnningLog){
            out.println(s);
        }
    }

    public void log(CTCommand cmd) {
        _runnningLog.add(cmd.ToString());
    }
}
