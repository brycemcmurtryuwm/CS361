package com.haxorz.lab12;

import java.util.Timer;
import java.util.TimerTask;

public class Light {
    public boolean _on = false;
    Timer _timer = null;

    public void onClick() {
        _on = !_on;

        if(_timer != null)
            _timer.cancel();
    }

    public void autoON() {
        _timer = new Timer();
        Light light = this;
        _on = true;

        try {
            _timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    light.off();
                }
            }, 20 * 1000);
        }catch(IllegalStateException ex){

        }

    }

    private void off() {
        _on = false;
    }


    public String toString(){
        return "The Light is " + (_on ? "ON" : "OFF") + "\n";
    }
}
