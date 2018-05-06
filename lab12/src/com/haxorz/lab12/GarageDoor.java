package com.haxorz.lab12;

public class GarageDoor {

    private DoorState _state = DoorState.STOPPED_WAS_CLOSING;
    private Motor _motor = new Motor();
    public StateMachine _machine;

    public GarageDoor(){
        _machine = new StateMachine(this);
    }

    public void open(){
        _motor.On();
        _motor.setReverse(true);
        _state = DoorState.OPENING;
    }

    public void close(){
        _motor.On();
        _motor.setReverse(false);
        _state = DoorState.CLOSING;
    }

    public void stop(){
        _motor.Off();
        _state = _motor.getReverse() ? DoorState.STOPPED_WAS_OPENING : DoorState.STOPPED_WAS_CLOSING;
        _motor.reverse();
    }

    public void onClick(){
        DoorState state = _machine.GetNext(DoorCmd.Click);

        switch (state){
            case OPENING:
            case CLOSING:
                _motor.On();
                break;
            case OPEN:
            case CLOSED:
                //not possible
                break;
            case STOPPED_WAS_OPENING:
            case STOPPED_WAS_CLOSING:
                _motor.Off();
                _motor.reverse();
                break;
        }

        _state = state;
    }

    public void onSafety(){
        open();
    }

    public void onLimit(){
        _motor.Off();
        _motor.reverse();
        _state = _machine.GetNext(DoorCmd.LimitStop);
    }

    public DoorState getState() {
        return _state;
    }

    @Override
    public String toString() {
        return "Door State -> " + _state.toString() + "\n";
    }
}
