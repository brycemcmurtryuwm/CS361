package com.haxorz.lab12;

import java.util.HashMap;

public class StateMachine {

    public HashMap<StateTransition, DoorState> transitions;

    private GarageDoor _garage;

    public StateMachine(GarageDoor system)
    {
        _garage = system;

        transitions = new HashMap<StateTransition, DoorState>();
        transitions.put( new StateTransition(DoorState.CLOSED, DoorCmd.Click), DoorState.OPENING);
        transitions.put( new StateTransition(DoorState.OPENING, DoorCmd.Click), DoorState.STOPPED_WAS_OPENING );
        transitions.put( new StateTransition(DoorState.OPENING, DoorCmd.LimitStop), DoorState.OPEN );
        transitions.put( new StateTransition(DoorState.STOPPED_WAS_OPENING, DoorCmd.Click), DoorState.CLOSING );
        transitions.put( new StateTransition(DoorState.STOPPED_WAS_CLOSING, DoorCmd.Click), DoorState.OPENING );
        transitions.put( new StateTransition(DoorState.CLOSING, DoorCmd.Click), DoorState.STOPPED_WAS_CLOSING );
        transitions.put( new StateTransition(DoorState.CLOSING, DoorCmd.LimitStop), DoorState.CLOSED );
        transitions.put( new StateTransition(DoorState.OPEN, DoorCmd.LimitStop), DoorState.OPEN );
        transitions.put( new StateTransition(DoorState.OPEN, DoorCmd.Click), DoorState.CLOSING );
    }


    public DoorState CurrentState(){
        return _garage == null ? DoorState.CLOSED : _garage.getState();
    }

    public DoorState GetNext(DoorCmd command)
    {
        StateTransition transition = new StateTransition(CurrentState(), command);
        if (!transitions.containsKey(transition))
            throw new IllegalStateException("Invalid transition: " + CurrentState() + " -> " + command);

        return transitions.get(transition);
    }

}

