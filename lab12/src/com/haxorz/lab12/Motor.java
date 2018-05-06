package com.haxorz.lab12;

public class Motor {

    public MotorState State;
    private boolean _on = false;
    private boolean _reversed = true;

    public void On(){
        _on = true;
        State = _reversed ? MotorState.REVERSING : MotorState.ON;
    }

    public void Off(){
        _on = false;
        State = MotorState.OFF;
    }

    public void reverse(){
        _reversed = !_reversed;
        State = _reversed ? MotorState.REVERSING : MotorState.ON;
    }

    public void setReverse(boolean reversed){
        _reversed = reversed;
        State = _reversed ? MotorState.REVERSING : MotorState.ON;
    }

    public boolean getReverse(){
        return _reversed;
    }

}
